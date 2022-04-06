package brewery.workshop.msscbrewery.web.controller;


import brewery.workshop.msscbrewery.service.BeerService;
import brewery.workshop.msscbrewery.web.model.BeerDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerController.class)
@RunWith(SpringRunner.class)
public class BeerControllerTest {

    @MockBean
    private BeerService  beerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private BeerDto validBeer;


    @Before
    public void setUp() {
        validBeer = BeerDto.builder().id(UUID.randomUUID())
                .beerName("Cals-Berg")
                .beerStyle("Party")
                .upc(1234567890L)
                .build();
    }


    @Test
    public void testGetBeer() throws Exception {
        given(beerService.getBeerById(any(UUID.class))).willReturn(validBeer);

        mockMvc.perform(get("/api/v1/beer/"+validBeer.getId().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.beerId", is(validBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(validBeer.getBeerName())))
                .andExpect(jsonPath("$.beerStyle", is(validBeer.getBeerStyle())));
    }

    @Test
    public void testHandlePost() throws Exception{
        BeerDto beerDto = validBeer;
        beerDto.setId(null);
        BeerDto savedBeer =  BeerDto.builder()
                .id(UUID.randomUUID())
                .beerName("New Beer")
                .build();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);
        given(beerService.saveNewBeer(any())).willReturn(savedBeer);
        mockMvc.perform(post("/api/v1/beer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void testHandlePut() throws Exception {
        //given
        String beerDtoJson = objectMapper.writeValueAsString(validBeer);
        //when
        mockMvc.perform(put("/api/v1/beer/" + validBeer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(beerDtoJson))
                .andExpect(status().isNoContent());
        //then
        then(beerService).should().updateBeer(any(), any());

    }
}

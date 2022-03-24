package brewery.workshop.msscbrewery.web.controller;


import brewery.workshop.msscbrewery.service.CustomerService;
import brewery.workshop.msscbrewery.web.model.CustomerDto;
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


@WebMvcTest(CustomerController.class)
@RunWith(SpringRunner.class)
public class CustomerControllerTest {

    @MockBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private CustomerDto customerDto;


    @Before
    public void setUp() {
        customerDto = CustomerDto.builder()
                .customerId(UUID.randomUUID())
                .customerName("customer")
                .build();
    }


    @Test
    public void testGetBeer() throws Exception {
        given(customerService.getCustomerById(any(UUID.class))).willReturn(customerDto);

        mockMvc.perform(get("/api/v1/customer/"+customerDto.getCustomerId().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.customerId", is(customerDto.getCustomerId().toString())))
                .andExpect(jsonPath("$.customerName", is(customerDto.getCustomerName())));
    }

    @Test
    public void testHandlePost() throws Exception{
       CustomerDto customer = customerDto;
       customer.setCustomerId(null);
       CustomerDto saveTheCustomer =  CustomerDto.builder()
                .customerId(UUID.randomUUID())
                .customerName("newCustomer")
                .build();
        String customerJson = objectMapper.writeValueAsString(customer);
        given(customerService.saveNewCustomer(any())).willReturn(saveTheCustomer);
        mockMvc.perform(post("/api/v1/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void testHandlePut() throws Exception {
        //given
        String beerDtoJson = objectMapper.writeValueAsString(customerDto);
        //when
        mockMvc.perform(put("/api/v1/customer/" + customerDto.getCustomerId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(beerDtoJson))
                .andExpect(status().isNoContent());
        //then
        then(customerService).should().updateCustomer(any(), any());

    }
}

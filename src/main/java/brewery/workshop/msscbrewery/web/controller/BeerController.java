package brewery.workshop.msscbrewery.web.controller;


import brewery.workshop.msscbrewery.service.BeerService;
import brewery.workshop.msscbrewery.web.model.BeerDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import java.util.UUID;

@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {
    private final BeerService beerService;

    public BeerController(BeerService beerService) {
         this.beerService =  beerService;
    }

    /*
     *GET- To Get Beer details
     */
    @GetMapping({"/{beerId}"})
    public ResponseEntity<BeerDto> getBeer(@PathVariable("beerId" ) UUID beerId) {
        return new ResponseEntity<>(beerService.getBeerById(beerId), HttpStatus.OK);
    }

    /*
     *POST- To Create new Beer
     */
    @PostMapping
    public ResponseEntity handlePost(@RequestBody BeerDto beerDto) {
        BeerDto  saveDto = beerService.saveNewBeer(beerDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        //TODO add host name to the URL, like http//:localhost:8080
        httpHeaders.add("Location",
                "/api/v1/beer"+saveDto.getId().toString());
        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
    }

    /*
     *PUT- To Update a Beer
     */
    @PutMapping({"/{beerId}"})
    public ResponseEntity handleUpdate(@PathVariable("beerId")UUID beerId
            , @RequestBody BeerDto beerDto) {

       beerService.updateBeer(beerId, beerDto);
       return  new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    /*
     *DELETE- To Delete a Beer
     */
    @DeleteMapping({"/{beerId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBeer(@PathVariable("beerId")UUID beerId) {
        beerService.deleteById(beerId);
    }
}

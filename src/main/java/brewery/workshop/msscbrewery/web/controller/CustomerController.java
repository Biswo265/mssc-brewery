package brewery.workshop.msscbrewery.web.controller;


import brewery.workshop.msscbrewery.service.CustomerService;
import brewery.workshop.msscbrewery.web.model.CustomerDto;
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
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /*
     *GET- To Get Customer details
     */
    @GetMapping({"/{customerId}"})
    public ResponseEntity<CustomerDto> getCustomer(
            @PathVariable("customerId")UUID customerId) {
        return new ResponseEntity<>(customerService
                .getCustomerById(customerId), HttpStatus.OK);
    }


    /*
     *POST- To Create new Customer
     */
    @PostMapping
    public ResponseEntity handlePost(@RequestBody CustomerDto customerDto) {
        CustomerDto  saveCustomer = customerService.saveNewCustomer(customerDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        //TODO add host name to the URL, like http//:localhost:8080
        httpHeaders.add("Location",
                "/api/v1/customer"+saveCustomer.getCustomerId().toString());
        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
    }

    /*
     *PUT- To Update a Customer Details
     */
    @PutMapping({"/{customerId}"})
    public ResponseEntity handleUpdate(@PathVariable("customerId")UUID customerId
            , @RequestBody CustomerDto customerDto) {

        customerService.updateCustomer(customerId, customerDto);
        return  new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    /*
     *DELETE- To Delete a Customer
     */
    @DeleteMapping({"/{customerId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBeer(@PathVariable("customerId")UUID customerId) {
        customerService.deleteById(customerId);
    }
}

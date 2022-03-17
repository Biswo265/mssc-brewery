package brewery.workshop.msscbrewery.service;

import brewery.workshop.msscbrewery.web.model.CustomerDto;

import java.util.UUID;

public interface CustomerService {
    CustomerDto getCustomerById(UUID customerId);
}

package brewery.workshop.msscbrewery.service;

import brewery.workshop.msscbrewery.web.model.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService{

    @Override
    public CustomerDto getCustomerById(UUID customerId) {
        return CustomerDto.builder()
                .customerId(UUID.randomUUID())
                .customerName("Prakash")
                .build();
    }

    @Override
    public CustomerDto saveNewCustomer(CustomerDto customerDto) {
       return CustomerDto.builder()
               .customerId(UUID.randomUUID())
               .customerName(customerDto.getCustomerName())
               .build();
    }

    @Override
    public void updateCustomer(UUID customerId, CustomerDto customerDto) {
        //TODO - Will Update to real impl
    }

    @Override
    public void deleteById(UUID customerId) {
        log.debug("Deleting a Customer Details....");
    }
}

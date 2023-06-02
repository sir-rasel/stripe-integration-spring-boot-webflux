package org.sir.stripeintegration.infrastructure.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.sir.stripeintegration.core.application.dtos.request.CustomerCreateRequestDto;
import org.sir.stripeintegration.core.application.dtos.request.CustomerUpdateRequestDto;
import org.sir.stripeintegration.core.application.dtos.response.CustomerDto;
import org.sir.stripeintegration.core.application.interfaces.service.ICustomerService;
import org.sir.stripeintegration.core.domain.CustomerEntity;
import org.sir.stripeintegration.core.shared.exceptions.CustomException;
import org.sir.stripeintegration.infrastructure.persistance.repository.CustomerRepository;
import org.sir.stripeintegration.infrastructure.service.stripe.StripeRootService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerService implements ICustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private final StripeRootService stripeRootService;
    private final CustomerRepository customerRepository;
    private final ModelMapper mapper = new ModelMapper();

    @Override
    public Mono<CustomerDto> getCustomer(String customerId) {
        Mono<CustomerEntity> customers = customerRepository.findById(customerId);
        return customers.map(customer -> mapper.map(customer, CustomerDto.class));
    }

    @Override
    public Flux<CustomerDto> getAllCustomer(Integer limit, String startingAfter, String endingBefore) {
        Flux<CustomerEntity> customers = customerRepository.findAll();
        return customers.map(customer -> mapper.map(customer, CustomerDto.class));
    }

    @Override
    public Mono<CustomerDto> addCustomer(CustomerCreateRequestDto requestDto) {
        try {
            CustomerDto customerDto = stripeRootService.createCustomer(requestDto);
            saveCustomerEntity(customerDto);
            return Mono.just(customerDto);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new CustomException("Error occurred on customer create");
        }
    }
    private void saveCustomerEntity(CustomerDto customerDto) {
        CustomerEntity customer = mapper.map(customerDto, CustomerEntity.class);
        customerRepository.save(customer);
    }

    @Override
    public Mono<CustomerDto> updateCustomer(CustomerUpdateRequestDto requestDto) {
        try {
            CustomerDto updatedCustomer = stripeRootService.updateCustomer(requestDto);
            updateCustomerEntity(requestDto);
            return Mono.just(updatedCustomer);
        }
        catch (CustomException ex){
            logger.error(ex.getMessage());
            throw new CustomException("Error occurred on customer update");
        }
    }
    private void updateCustomerEntity(CustomerUpdateRequestDto requestDto){
        Mono<CustomerEntity> customer = customerRepository.findById(requestDto.id);
        CustomerEntity customerEntity = customer.block();

        assert customerEntity != null;
        customerEntity.setEmail(requestDto.email);
        customerEntity.setName(requestDto.name);
        customerEntity.setPhone(requestDto.phone);

        customerRepository.save(customerEntity);
    }

    @Override
    public Mono<CustomerDto> deleteCustomer(String customerId) {
        try {
            deleteCustomerEntity(customerId);
            CustomerDto deletedCustomer = stripeRootService.deleteCustomer(customerId);
            return Mono.just(deletedCustomer);
        }
        catch (CustomException ex){
            logger.error(ex.getMessage());
            throw new CustomException("Error occurred on customer delete");
        }
    }
    private void deleteCustomerEntity(String customerId){
        customerRepository.deleteById(customerId);
    }
}

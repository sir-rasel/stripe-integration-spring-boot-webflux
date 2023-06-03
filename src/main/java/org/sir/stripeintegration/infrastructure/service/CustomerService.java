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

import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerService implements ICustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerRepository customerRepository;

    private final StripeRootService stripeRootService;
    private final ModelMapper mapper = new ModelMapper();

    @Override
    public Mono<CustomerDto> getCustomer(UUID customerId) {
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
            Mono<CustomerEntity> customer = saveCustomerEntity(customerDto);
            return customer.map(customerEntity -> mapper.map(customerEntity, CustomerDto.class));
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new CustomException("Error occurred on customer create");
        }
    }
    private Mono<CustomerEntity> saveCustomerEntity(CustomerDto customerDto) {
        CustomerEntity customer = mapper.map(customerDto, CustomerEntity.class);
        return customerRepository.save(customer);
    }

    @Override
    public Mono<CustomerDto> updateCustomer(CustomerUpdateRequestDto requestDto) {
        try {
            return updateCustomerEntity(requestDto).map(customerEntity -> {
                requestDto.setCustomerId(customerEntity.getCustomerId());

                CustomerDto customerDto = stripeRootService.updateCustomer(requestDto);
                customerDto.setId(requestDto.getId());
                return customerDto;
            });
        }
        catch (CustomException ex){
            logger.error(ex.getMessage());
            throw new CustomException("Error occurred on customer update");
        }
    }
    private Mono<CustomerEntity> updateCustomerEntity(CustomerUpdateRequestDto requestDto){
        Mono<CustomerEntity> customer = customerRepository.findById(requestDto.id);
       return customer.flatMap(customerEntity -> {
            customerEntity.setEmail(requestDto.email);
            customerEntity.setName(requestDto.name);
            customerEntity.setPhone(requestDto.phone);

            return customerRepository.save(customerEntity);
        });
    }

    @Override
    public Mono<Void> deleteCustomer(UUID id) {
        try {
            return getCustomer(id).flatMap(customerDto -> {
                CustomerDto deletedCustomer = stripeRootService.deleteCustomer(customerDto.customerId);
                return deleteCustomerEntity(id);
            });
        }
        catch (CustomException ex){
            logger.error(ex.getMessage());
            throw new CustomException("Error occurred on customer delete");
        }
    }
    private Mono<Void> deleteCustomerEntity(UUID id){
        return customerRepository.deleteById(id);
    }
}

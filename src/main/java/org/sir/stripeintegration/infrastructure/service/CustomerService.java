package org.sir.stripeintegration.infrastructure.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.sir.stripeintegration.core.application.dtos.customer.request.CustomerCreateRequestDto;
import org.sir.stripeintegration.core.application.dtos.customer.request.CustomerUpdateRequestDto;
import org.sir.stripeintegration.core.application.dtos.customer.response.CustomerDto;
import org.sir.stripeintegration.core.application.interfaces.service.ICustomerService;
import org.sir.stripeintegration.core.domain.CustomerEntity;
import org.sir.stripeintegration.core.shared.constant.ErrorMessage;
import org.sir.stripeintegration.core.shared.exceptions.CustomException;
import org.sir.stripeintegration.infrastructure.persistance.repository.CustomerRepository;
import org.sir.stripeintegration.infrastructure.service.stripe.StripeRootService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerService implements ICustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerRepository customerRepository;

    private final StripeRootService stripeRootService;
    private final ModelMapper mapper = new ModelMapper();

    @Override
    public Mono<CustomerDto> getCustomer(String customerId) {
        Mono<CustomerEntity> customers = customerRepository.findById(customerId);
        return customers.map(customer -> mapper.map(customer, CustomerDto.class))
                .switchIfEmpty(Mono.error(new CustomException(ErrorMessage.CUSTOMER_NOT_FOUND.getMessage())));
    }

    @Override
    public Flux<CustomerDto> getAllCustomer(Integer limit, String startingAfter, String endingBefore) {
        List<CustomerDto> customers = stripeRootService.getAllCustomers(limit, startingAfter, endingBefore);
        return Mono.just(customers).flatMapIterable(list -> list);
    }

    @Override
    public Mono<CustomerDto> addCustomer(CustomerCreateRequestDto requestDto) {
        try {
            CustomerDto customerDto = stripeRootService.createCustomer(requestDto);
            Mono<CustomerEntity> customer = saveCustomerEntity(customerDto);
            return customer.map(customerEntity -> mapper.map(customerEntity, CustomerDto.class));
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new CustomException("Error occurred on customer create");
        }
    }
    private Mono<CustomerEntity> saveCustomerEntity(CustomerDto customerDto) {
        CustomerEntity customer = mapper.map(customerDto, CustomerEntity.class);
        customer.setNewEntry(true);

        return customerRepository.save(customer);
    }

    @Override
    public Mono<CustomerDto> updateCustomer(CustomerUpdateRequestDto requestDto) {
        try {
            return updateCustomerEntity(requestDto)
                    .switchIfEmpty(Mono.error(new CustomException(ErrorMessage.CUSTOMER_NOT_FOUND.getMessage())))
                    .map(customerEntity -> {
                        CustomerDto customerDto = stripeRootService.updateCustomer(requestDto);
                        customerDto.setId(requestDto.id);
                        return customerDto;
                    });
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new CustomException("Error occurred on customer update");
        }
    }
    private Mono<CustomerEntity> updateCustomerEntity(CustomerUpdateRequestDto requestDto) {
        Mono<CustomerEntity> customer = customerRepository.findById(requestDto.id);
        return customer.flatMap(customerEntity -> {
            customerEntity.setEmail(requestDto.email);
            customerEntity.setName(requestDto.name);
            customerEntity.setPhone(requestDto.phone);
            customerEntity.setNewEntry(false);

            return customerRepository.save(customerEntity);
        });
    }

    @Override
    public Mono<Void> deleteCustomer(String id) {
        try {
            return getCustomer(id).flatMap(customerDto -> {
                stripeRootService.deleteCustomer(customerDto.id);
                return deleteCustomerEntity(id);
            });
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new CustomException("Error occurred on customer delete");
        }
    }
    private Mono<Void> deleteCustomerEntity(String id) {
        return customerRepository.deleteById(id);
    }
}

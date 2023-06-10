package org.sir.stripeintegration.core.application.interfaces.service;

import org.sir.stripeintegration.core.application.dtos.customer.request.CustomerCreateRequestDto;
import org.sir.stripeintegration.core.application.dtos.customer.request.CustomerUpdateRequestDto;
import org.sir.stripeintegration.core.application.dtos.customer.response.CustomerDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ICustomerService {
    Mono<CustomerDto> getCustomer(String id);
    Flux<CustomerDto> getAllCustomer(Integer limit, String startingAfter, String endingBefore);
    Mono<CustomerDto> addCustomer(CustomerCreateRequestDto requestDto);
    Mono<CustomerDto> updateCustomer(CustomerUpdateRequestDto requestDto);
    Mono<Void> deleteCustomer(String id);
}

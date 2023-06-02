package org.sir.stripeintegration.core.application.interfaces.service;

import org.sir.stripeintegration.core.application.dtos.request.CustomerCreateRequestDto;
import org.sir.stripeintegration.core.application.dtos.request.CustomerUpdateRequestDto;
import org.sir.stripeintegration.core.application.dtos.response.CustomerDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICustomerService {
    Mono<CustomerDto> getCustomer(String customerId);
    Flux<CustomerDto> getAllCustomer(Integer limit, String startingAfter, String endingBefore);
    Mono<CustomerDto> addCustomer(CustomerCreateRequestDto requestDto);
    Mono<CustomerDto> updateCustomer(CustomerUpdateRequestDto requestDto);
    Mono<CustomerDto> deleteCustomer(String customerId);
}

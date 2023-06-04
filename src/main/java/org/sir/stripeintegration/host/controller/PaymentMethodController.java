package org.sir.stripeintegration.host.controller;

import lombok.AllArgsConstructor;
import org.sir.stripeintegration.core.application.dtos.customer.request.CustomerCreateRequestDto;
import org.sir.stripeintegration.core.application.dtos.customer.request.CustomerUpdateRequestDto;
import org.sir.stripeintegration.core.application.dtos.customer.response.CustomerDto;
import org.sir.stripeintegration.core.application.interfaces.service.IPaymentMethodService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/payment-method")
public class PaymentMethodController {
    private final IPaymentMethodService paymentMethodService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<CustomerDto> getPaymentMethod(@PathVariable UUID id) {
        return customerService.getCustomer(id);
    }

    @GetMapping("customer/{customerId}/all")
    @ResponseStatus(HttpStatus.OK)
    public Flux<CustomerDto> getCustomerAllPaymentMethod(
            @PathVariable String customerId,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) String startingAfter,
            @RequestParam(required = false) String endingBefore) {
        return customerService.getAllCustomer(limit, startingAfter, endingBefore);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CustomerDto> addCustomerPaymentMethod(@RequestBody CustomerCreateRequestDto requestDto) {
        return customerService.addCustomer(requestDto);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public Mono<CustomerDto> updateCustomerPaymentMethod(@RequestBody CustomerUpdateRequestDto requestDto) {
        return customerService.updateCustomer(requestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deletePaymentMethod(@PathVariable UUID id) {
        return customerService.deleteCustomer(id);
    }
}

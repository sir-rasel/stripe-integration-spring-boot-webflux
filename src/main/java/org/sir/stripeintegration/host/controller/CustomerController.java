package org.sir.stripeintegration.host.controller;

import lombok.AllArgsConstructor;
import org.sir.stripeintegration.core.application.dtos.request.CustomerCreateRequestDto;
import org.sir.stripeintegration.core.application.dtos.request.CustomerUpdateRequestDto;
import org.sir.stripeintegration.core.application.dtos.response.CustomerDto;
import org.sir.stripeintegration.core.application.interfaces.service.ICustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final ICustomerService customerService;

    @GetMapping("/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<CustomerDto> getCustomer(@PathVariable String customerId) {
        return customerService.getCustomer(customerId);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Flux<CustomerDto> getAllCustomer(
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) String startingAfter,
            @RequestParam(required = false) String endingBefore) {
        return customerService.getAllCustomer(limit, startingAfter, endingBefore);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CustomerDto> addCustomer(@RequestBody CustomerCreateRequestDto requestDto) {
        return customerService.addCustomer(requestDto);
    }

    @PutMapping("/update/")
    @ResponseStatus(HttpStatus.OK)
    public Mono<CustomerDto> updateCustomer(@RequestBody CustomerUpdateRequestDto requestDto) {
        return customerService.updateCustomer(requestDto);
    }

    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<CustomerDto> deleteCustomer(@PathVariable String customerId) {
        return customerService.deleteCustomer(customerId);
    }
}

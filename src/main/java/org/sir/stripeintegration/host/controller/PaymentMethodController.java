package org.sir.stripeintegration.host.controller;

import lombok.AllArgsConstructor;
import org.sir.stripeintegration.core.application.dtos.paymentMethod.request.CreatePaymentMethodRequestDto;
import org.sir.stripeintegration.core.application.dtos.paymentMethod.request.UpdatePaymentMethodRequestDto;
import org.sir.stripeintegration.core.application.dtos.paymentMethod.response.PaymentMethodDto;
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
    public Mono<PaymentMethodDto> getPaymentMethod(@PathVariable UUID id) {
        return paymentMethodService.getPaymentMethod(id);
    }

    @GetMapping("customer/{customerId}/all")
    @ResponseStatus(HttpStatus.OK)
    public Flux<PaymentMethodDto> getCustomerAllPaymentMethod(
            @PathVariable String customerId,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) String startingAfter,
            @RequestParam(required = false) String endingBefore) {
        return paymentMethodService.getCustomerAllPaymentMethod(
                customerId, limit, startingAfter, endingBefore);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
        public Mono<PaymentMethodDto> addCustomerPaymentMethod(
            @RequestBody CreatePaymentMethodRequestDto requestDto) {
        return paymentMethodService.addCustomerPaymentMethod(requestDto);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public Mono<PaymentMethodDto> updateCustomerPaymentMethod(
            @RequestBody UpdatePaymentMethodRequestDto requestDto) {
        return paymentMethodService.updateCustomerPaymentMethod(requestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deletePaymentMethod(@PathVariable UUID id) {
        return paymentMethodService.deletePaymentMethod(id);
    }
}

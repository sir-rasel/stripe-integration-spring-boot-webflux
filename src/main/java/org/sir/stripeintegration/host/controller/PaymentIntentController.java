package org.sir.stripeintegration.host.controller;

import lombok.AllArgsConstructor;
import org.sir.stripeintegration.core.application.dtos.paymentIntent.request.CreatePaymentIntentRequestDto;
import org.sir.stripeintegration.core.application.dtos.paymentIntent.response.PaymentIntentDto;
import org.sir.stripeintegration.core.application.dtos.paymentMethod.request.CreatePaymentMethodRequestDto;
import org.sir.stripeintegration.core.application.dtos.paymentMethod.response.PaymentMethodDto;
import org.sir.stripeintegration.core.application.interfaces.service.IPaymentIntentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/api/payment-intent")
public class PaymentIntentController {
    private final IPaymentIntentService paymentIntentService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<PaymentIntentDto> getPaymentIntent(@PathVariable String id) {
        return paymentIntentService.getPaymentIntent(id);
    }

    @GetMapping("/all/customer/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<PaymentIntentDto> getCustomerAllPaymentIntent(
            @PathVariable String customerId,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) String startingAfter,
            @RequestParam(required = false) String endingBefore) {
        return paymentIntentService.getCustomerAllPaymentIntent(customerId, limit, startingAfter, endingBefore);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<PaymentMethodDto> addCustomerPaymentIntent(
            @RequestBody CreatePaymentIntentRequestDto requestDto) {
        return paymentIntentService.addCustomerPaymentIntent(requestDto);
    }
}

package org.sir.stripeintegration.host.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.sir.stripeintegration.core.application.dtos.subscription.request.CreateSubscriptionDto;
import org.sir.stripeintegration.core.application.dtos.subscription.request.UpdateSubscriptionDto;
import org.sir.stripeintegration.core.application.dtos.subscription.response.SubscriptionDto;
import org.sir.stripeintegration.core.application.interfaces.service.ISubscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {
    private final ISubscriptionService subscriptionService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<SubscriptionDto> getSubscription(@PathVariable String id) {
        return subscriptionService.getSubscription(id);
    }

    @GetMapping("/all/customer/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<SubscriptionDto> getCustomerAllSubscription(
            @PathVariable String customerId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long limit,
            @RequestParam(required = false) String startingAfter,
            @RequestParam(required = false) String endingBefore) {
        return subscriptionService.getCustomerAllSubscription(
                customerId, status, limit, startingAfter, endingBefore);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<SubscriptionDto> addSubscription(@RequestBody @Valid CreateSubscriptionDto requestDto) {
        return subscriptionService.addSubscription(requestDto);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public Mono<SubscriptionDto> updateSubscription(@RequestBody @Valid UpdateSubscriptionDto requestDto) {
        return subscriptionService.updateSubscription(requestDto);
    }

    @DeleteMapping("/cancel/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<SubscriptionDto> cancelSubscription(@PathVariable String id) {
        return subscriptionService.cancelSubscription(id);
    }
}

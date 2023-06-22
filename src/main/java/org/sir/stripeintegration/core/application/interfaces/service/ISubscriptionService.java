package org.sir.stripeintegration.core.application.interfaces.service;

import org.sir.stripeintegration.core.application.dtos.subscription.request.CreateSubscriptionDto;
import org.sir.stripeintegration.core.application.dtos.subscription.request.UpdateSubscriptionDto;
import org.sir.stripeintegration.core.application.dtos.subscription.response.SubscriptionDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ISubscriptionService {
    Mono<SubscriptionDto> getSubscription(String id);

    Flux<SubscriptionDto> getCustomerAllSubscription(
            String customerId, String status, Long limit, String startingAfter, String endingBefore);

    Mono<SubscriptionDto> addSubscription(CreateSubscriptionDto requestDto);

    Mono<SubscriptionDto> updateSubscription(UpdateSubscriptionDto requestDto);

    Mono<SubscriptionDto> cancelSubscription(String id);
}

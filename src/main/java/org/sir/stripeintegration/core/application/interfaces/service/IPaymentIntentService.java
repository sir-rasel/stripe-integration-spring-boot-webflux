package org.sir.stripeintegration.core.application.interfaces.service;

import org.sir.stripeintegration.core.application.dtos.paymentIntent.request.CreatePaymentIntentRequestDto;
import org.sir.stripeintegration.core.application.dtos.paymentIntent.response.PaymentIntentDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IPaymentIntentService {
    Mono<PaymentIntentDto> getPaymentIntent(String id);
    Flux<PaymentIntentDto> getCustomerAllPaymentIntent(
            String customerId, Long limit, String startingAfter, String endingBefore);
    Mono<PaymentIntentDto> addCustomerPaymentIntent(CreatePaymentIntentRequestDto requestDto);
}

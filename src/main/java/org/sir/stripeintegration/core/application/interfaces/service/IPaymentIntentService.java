package org.sir.stripeintegration.core.application.interfaces.service;

import org.sir.stripeintegration.core.application.dtos.paymentIntent.request.CreatePaymentIntentRequestDto;
import org.sir.stripeintegration.core.application.dtos.paymentIntent.response.PaymentIntentDto;
import org.sir.stripeintegration.core.application.dtos.paymentMethod.response.PaymentMethodDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IPaymentIntentService {
    Mono<PaymentIntentDto> getPaymentIntent(String id);
    Flux<PaymentIntentDto> getCustomerAllPaymentIntent(
            String customerId, Integer limit, String startingAfter, String endingBefore);
    Mono<PaymentMethodDto> addCustomerPaymentIntent(CreatePaymentIntentRequestDto requestDto);
}

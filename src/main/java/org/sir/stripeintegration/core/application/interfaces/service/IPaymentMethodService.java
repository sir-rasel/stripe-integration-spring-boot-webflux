package org.sir.stripeintegration.core.application.interfaces.service;

import org.sir.stripeintegration.core.application.dtos.paymentMethod.request.CreatePaymentMethodRequestDto;
import org.sir.stripeintegration.core.application.dtos.paymentMethod.request.UpdatePaymentMethodRequestDto;
import org.sir.stripeintegration.core.application.dtos.paymentMethod.response.PaymentMethodDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IPaymentMethodService {
    Mono<PaymentMethodDto> getPaymentMethod(UUID id);
    Flux<PaymentMethodDto> getCustomerAllPaymentMethod(
            String customerId,Integer limit, String startingAfter, String endingBefore);
    Mono<PaymentMethodDto> addCustomerPaymentMethod(CreatePaymentMethodRequestDto requestDto);
    Mono<PaymentMethodDto> updateCustomerPaymentMethod(UpdatePaymentMethodRequestDto requestDto);
    Mono<Void> deletePaymentMethod(UUID id);
}

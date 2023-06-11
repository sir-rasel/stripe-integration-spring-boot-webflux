package org.sir.stripeintegration.infrastructure.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.sir.stripeintegration.core.application.dtos.paymentIntent.request.CreatePaymentIntentRequestDto;
import org.sir.stripeintegration.core.application.dtos.paymentIntent.response.PaymentIntentDto;
import org.sir.stripeintegration.core.application.dtos.paymentMethod.response.PaymentMethodDto;
import org.sir.stripeintegration.infrastructure.persistance.repository.PaymentIntentRepository;
import org.sir.stripeintegration.core.application.interfaces.service.IPaymentIntentService;
import org.sir.stripeintegration.infrastructure.service.stripe.StripeRootService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class PaymentIntentService implements IPaymentIntentService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentIntentService.class);
    private final PaymentIntentRepository paymentIntentRepository;

    private final StripeRootService stripeRootService;
    private final ModelMapper mapper = new ModelMapper();

    @Override
    public Mono<PaymentIntentDto> getPaymentIntent(String id) {
        return null;
    }

    @Override
    public Flux<PaymentIntentDto> getCustomerAllPaymentIntent(
            String customerId, Integer limit, String startingAfter, String endingBefore) {
        return null;
    }

    @Override
    public Mono<PaymentMethodDto> addCustomerPaymentIntent(CreatePaymentIntentRequestDto requestDto) {
        return null;
    }
}

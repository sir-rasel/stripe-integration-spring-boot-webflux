package org.sir.stripeintegration.infrastructure.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.sir.stripeintegration.core.application.dtos.paymentIntent.request.CreatePaymentIntentRequestDto;
import org.sir.stripeintegration.core.application.dtos.paymentIntent.response.PaymentIntentDto;
import org.sir.stripeintegration.core.application.interfaces.service.IPaymentIntentService;
import org.sir.stripeintegration.core.domain.PaymentIntentEntity;
import org.sir.stripeintegration.core.shared.constant.ErrorMessage;
import org.sir.stripeintegration.core.shared.exceptions.CustomException;
import org.sir.stripeintegration.infrastructure.persistance.repository.CustomerRepository;
import org.sir.stripeintegration.infrastructure.persistance.repository.PaymentIntentRepository;
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
    private final CustomerRepository customerRepository;

    private final StripeRootService stripeRootService;
    private final ModelMapper mapper = new ModelMapper();

    @Override
    public Mono<PaymentIntentDto> getPaymentIntent(String id) {
        return paymentIntentRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(ErrorMessage.PAYMENT_INTENT_NOT_FOUND.getMessage())))
                .map(paymentIntentEntity -> stripeRootService.getCustomerPaymentIntentById(
                        id, paymentIntentEntity.customerId));
    }

    @Override
    public Flux<PaymentIntentDto> getCustomerAllPaymentIntent(
            String customerId, Long limit, String startingAfter, String endingBefore) {
        return customerRepository.findById(customerId)
                .switchIfEmpty(Mono.error(new CustomException(ErrorMessage.CUSTOMER_NOT_FOUND.getMessage())))
                .map(customerEntity -> stripeRootService.getCustomerAllPaymentIntents(
                        customerId, limit, startingAfter, endingBefore))
                .flatMapIterable(list -> list);
    }

    @Override
    public Mono<PaymentIntentDto> addCustomerPaymentIntent(CreatePaymentIntentRequestDto requestDto) {

        return customerRepository.findById(requestDto.customerId)
                .switchIfEmpty(Mono.error(new CustomException(ErrorMessage.CUSTOMER_NOT_FOUND.getMessage())))
                .flatMap(customerEntity -> {
                    try {
                        PaymentIntentDto paymentIntentDto = stripeRootService.createPaymentIntent(requestDto);

                        return savePaymentIntentEntity(paymentIntentDto)
                                .map(paymentMethodEntity -> paymentIntentDto);
                    } catch (Exception ex) {
                        logger.error(ex.getMessage());
                        throw new CustomException("Error on customer payment intent create");
                    }
                });
    }

    private Mono<PaymentIntentEntity> savePaymentIntentEntity(PaymentIntentDto paymentIntentDto) {
        PaymentIntentEntity paymentIntentEntity = mapper.map(paymentIntentDto, PaymentIntentEntity.class);
        paymentIntentEntity.setNewEntry(true);

        return paymentIntentRepository.save(paymentIntentEntity);
    }
}

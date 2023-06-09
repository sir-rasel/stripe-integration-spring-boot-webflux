package org.sir.stripeintegration.infrastructure.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.sir.stripeintegration.core.application.dtos.paymentMethod.request.CreatePaymentMethodRequestDto;
import org.sir.stripeintegration.core.application.dtos.paymentMethod.request.UpdatePaymentMethodRequestDto;
import org.sir.stripeintegration.core.application.dtos.paymentMethod.response.PaymentMethodDto;
import org.sir.stripeintegration.core.domain.PaymentMethodEntity;
import org.sir.stripeintegration.core.shared.exceptions.CustomException;
import org.sir.stripeintegration.infrastructure.persistance.repository.CustomerRepository;
import org.sir.stripeintegration.infrastructure.persistance.repository.PaymentMethodRepository;
import org.sir.stripeintegration.core.application.interfaces.service.IPaymentMethodService;
import org.sir.stripeintegration.infrastructure.service.stripe.StripeRootService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class PaymentMethodService implements IPaymentMethodService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentMethodService.class);
    private final PaymentMethodRepository paymentMethodRepository;
    private final CustomerRepository customerRepository;

    private final StripeRootService stripeRootService;
    private final ModelMapper mapper = new ModelMapper();

    @Override
    public Mono<PaymentMethodDto> getPaymentMethod(UUID id) {
        return null;
    }

    @Override
    public Flux<PaymentMethodDto> getCustomerAllPaymentMethod(
            String customerId, Integer limit, String startingAfter, String endingBefore) {
        return null;
    }

    @Override
    public Mono<PaymentMethodDto> addCustomerPaymentMethod(CreatePaymentMethodRequestDto requestDto) {
        return customerRepository.findByCustomerId(requestDto.customerId)
                .map(customerEntity -> {
                    try {
                        PaymentMethodDto paymentMethodDto =
                                stripeRootService.createPaymentMethod(customerEntity, requestDto);

                        PaymentMethodEntity paymentMethodEntity
                                = savePaymentMethod(paymentMethodDto).block();

                        paymentMethodDto.id = paymentMethodEntity.id;
                        return paymentMethodDto;
                    } catch (CustomException ex) {
                        logger.error(ex.getMessage());
                        throw new CustomException("Error on customer payment method crete");
                    }
                })
                .switchIfEmpty(Mono.error(new CustomException("Customer not found")));
    }

    private Mono<PaymentMethodEntity> savePaymentMethod(PaymentMethodDto paymentMethodDto) {
        PaymentMethodEntity paymentMethodEntity =
                mapper.map(paymentMethodDto, PaymentMethodEntity.class);
        return paymentMethodRepository.save(paymentMethodEntity);
    }

    @Override
    public Mono<PaymentMethodDto> updateCustomerPaymentMethod(UpdatePaymentMethodRequestDto requestDto) {
        return null;
    }

    @Override
    public Mono<Void> deletePaymentMethod(UUID id) {
        return null;
    }
}

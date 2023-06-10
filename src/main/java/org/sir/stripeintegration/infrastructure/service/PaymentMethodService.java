package org.sir.stripeintegration.infrastructure.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.sir.stripeintegration.core.application.dtos.paymentMethod.request.CreatePaymentMethodRequestDto;
import org.sir.stripeintegration.core.application.dtos.paymentMethod.request.UpdatePaymentMethodRequestDto;
import org.sir.stripeintegration.core.application.dtos.paymentMethod.response.PaymentMethodDto;
import org.sir.stripeintegration.core.application.interfaces.service.IPaymentMethodService;
import org.sir.stripeintegration.core.domain.PaymentMethodEntity;
import org.sir.stripeintegration.core.shared.constant.ErrorMessage;
import org.sir.stripeintegration.core.shared.exceptions.CustomException;
import org.sir.stripeintegration.infrastructure.persistance.repository.CustomerRepository;
import org.sir.stripeintegration.infrastructure.persistance.repository.PaymentMethodRepository;
import org.sir.stripeintegration.infrastructure.service.stripe.StripeRootService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Mono<PaymentMethodDto> getPaymentMethod(String id) {
        return paymentMethodRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(ErrorMessage.PAYMENT_METHOD_NOT_FOUND.getMessage())))
                .map(paymentMethodEntity -> stripeRootService.getCustomerPaymentMethodById(
                        id, paymentMethodEntity.customerId));
    }

    @Override
    public Flux<PaymentMethodDto> getCustomerAllPaymentMethod(
            String customerId, Integer limit, String startingAfter, String endingBefore) {
        return customerRepository.findById(customerId)
                .switchIfEmpty(Mono.error(new CustomException(ErrorMessage.CUSTOMER_NOT_FOUND.getMessage())))
                .map(customerEntity -> stripeRootService.getCustomerAllPaymentMethods(
                        customerId, limit, startingAfter, endingBefore))
                .flatMapIterable(list -> list);
    }

    @Override
    public Mono<PaymentMethodDto> addCustomerPaymentMethod(CreatePaymentMethodRequestDto requestDto) {
        return customerRepository.findById(requestDto.customerId)
                .switchIfEmpty(Mono.error(new CustomException(ErrorMessage.CUSTOMER_NOT_FOUND.getMessage())))
                .flatMap(customerEntity -> {
                    try {
                        PaymentMethodDto paymentMethodDto = stripeRootService.createPaymentMethod(
                                customerEntity, requestDto);

                        return savePaymentMethodEntity(paymentMethodDto)
                                .doOnNext(paymentMethodEntity -> paymentMethodDto.id = paymentMethodEntity.id)
                                .map(paymentMethodEntity -> paymentMethodDto);
                    } catch (CustomException ex) {
                        logger.error(ex.getMessage());
                        throw new CustomException("Error on customer payment method crete");
                    }
                });
    }

    private Mono<PaymentMethodEntity> savePaymentMethodEntity(PaymentMethodDto paymentMethodDto) {
        PaymentMethodEntity paymentMethodEntity = mapper.map(paymentMethodDto, PaymentMethodEntity.class);
        paymentMethodEntity.setNewEntry(true);

        return paymentMethodRepository.save(paymentMethodEntity);
    }

    @Override
    public Mono<PaymentMethodDto> updateCustomerPaymentMethod(UpdatePaymentMethodRequestDto requestDto) {
        return paymentMethodRepository.findById(requestDto.id)
                .switchIfEmpty(Mono.error(new CustomException(ErrorMessage.PAYMENT_METHOD_NOT_FOUND.getMessage())))
                .map(paymentMethodEntity -> stripeRootService.updatePaymentMethod(requestDto));
    }

    @Override
    public Mono<Void> deletePaymentMethod(String id) {
        return paymentMethodRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(ErrorMessage.PAYMENT_METHOD_NOT_FOUND.getMessage())))
                .flatMap(paymentMethodEntity -> {
                    stripeRootService.deletePaymentMethod(id);
                    return deletePaymentMethodEntity(id);
                });
    }

    private Mono<Void> deletePaymentMethodEntity(String id) {
        return paymentMethodRepository.deleteById(id);
    }

    @Override
    public Mono<PaymentMethodDto> setCustomerDefaultPaymentMethod(String customerId, String paymentMethodId) {
        return customerRepository.findById(customerId)
                .switchIfEmpty(Mono.error(new CustomException(ErrorMessage.CUSTOMER_NOT_FOUND.getMessage())))
                .map(customer -> stripeRootService.setCustomerDefaultPaymentMethod(customerId, paymentMethodId));
    }
}

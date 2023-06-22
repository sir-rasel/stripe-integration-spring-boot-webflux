package org.sir.stripeintegration.infrastructure.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.sir.stripeintegration.core.application.dtos.subscription.request.CreateSubscriptionDto;
import org.sir.stripeintegration.core.application.dtos.subscription.request.UpdateSubscriptionDto;
import org.sir.stripeintegration.core.application.dtos.subscription.response.SubscriptionDto;
import org.sir.stripeintegration.core.application.interfaces.service.ISubscriptionService;
import org.sir.stripeintegration.core.domain.SubscriptionEntity;
import org.sir.stripeintegration.core.shared.constant.ErrorMessage;
import org.sir.stripeintegration.core.shared.exceptions.CustomException;
import org.sir.stripeintegration.infrastructure.persistance.repository.CustomerRepository;
import org.sir.stripeintegration.infrastructure.persistance.repository.SubscriptionRepository;
import org.sir.stripeintegration.infrastructure.service.stripe.StripeRootService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class SubscriptionService implements ISubscriptionService {
    private static final Logger logger = LoggerFactory.getLogger(SubscriptionService.class);
    private final CustomerRepository customerRepository;
    private final SubscriptionRepository subscriptionRepository;

    private final StripeRootService stripeRootService;
    private final ModelMapper mapper = new ModelMapper();

    @Override
    public Mono<SubscriptionDto> getSubscription(String id) {
        return subscriptionRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(ErrorMessage.SUBSCRIPTION_NOT_FOUND.getMessage())))
                .map(subscriptionEntity -> stripeRootService.getSubscription(id));
    }

    @Override
    public Flux<SubscriptionDto> getCustomerAllSubscription(
            String customerId, String status, Long limit, String startingAfter, String endingBefore) {
        return customerRepository.findById(customerId)
                .switchIfEmpty(Mono.error(new CustomException(ErrorMessage.CUSTOMER_NOT_FOUND.getMessage())))
                .map(customerEntity -> stripeRootService.getCustomerAllSubscriptions(
                        customerId, status, limit, startingAfter, endingBefore))
                .flatMapIterable(list -> list);
    }

    @Override
    public Mono<SubscriptionDto> addSubscription(CreateSubscriptionDto requestDto) {
        return customerRepository.findById(requestDto.customerId)
                .switchIfEmpty(Mono.error(new CustomException(ErrorMessage.CUSTOMER_NOT_FOUND.getMessage())))
                .flatMap(customerEntity -> {
                    try {
                        SubscriptionDto subscriptionDto = stripeRootService.createSubscription(requestDto);

                        return saveSubscriptionEntity(subscriptionDto)
                                .map(subscriptionEntity -> subscriptionDto);
                    } catch (Exception ex) {
                        logger.error(ex.getMessage());
                        return Mono.error(new CustomException("Error on subscription create"));
                    }
                });
    }

    private Mono<SubscriptionEntity> saveSubscriptionEntity(SubscriptionDto subscriptionDto) {
        SubscriptionEntity subscriptionEntity = mapper.map(subscriptionDto, SubscriptionEntity.class);
        subscriptionEntity.setNewEntry(true);

        return subscriptionRepository.save(subscriptionEntity);
    }

    @Override
    public Mono<SubscriptionDto> updateSubscription(UpdateSubscriptionDto requestDto) {
        return updateSubscriptionEntity(requestDto)
                .switchIfEmpty(Mono.error(new CustomException(ErrorMessage.SUBSCRIPTION_NOT_FOUND.getMessage())))
                .map(subscriptionEntity -> stripeRootService.updateSubscription(requestDto));
    }

    private Mono<SubscriptionEntity> updateSubscriptionEntity(UpdateSubscriptionDto requestDto) {
        return subscriptionRepository.findById(requestDto.id)
                .flatMap(subscriptionEntity -> {
                    subscriptionEntity.setCancelAt(requestDto.cancelAt);
                    subscriptionEntity.setDescription(requestDto.description);
                    subscriptionEntity.setNewEntry(false);

                    return subscriptionRepository.save(subscriptionEntity);
                });
    }
    
    @Override
    public Mono<SubscriptionDto> cancelSubscription(String id) {
        return updateSubscriptionEntityStatus(id, "canceled")
                .switchIfEmpty(Mono.error(new CustomException(ErrorMessage.SUBSCRIPTION_NOT_FOUND.getMessage())))
                .map(subscriptionEntity -> stripeRootService.cancelSubscription(id));
    }

    private Mono<SubscriptionEntity> updateSubscriptionEntityStatus(String id, String status) {
        Mono<SubscriptionEntity> subscription = subscriptionRepository.findById(id);
        return subscription.flatMap(subscriptionEntity -> {
            subscriptionEntity.setStatus(status);
            subscriptionEntity.setNewEntry(false);

            return subscriptionRepository.save(subscriptionEntity);
        });
    }
}

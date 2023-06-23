package org.sir.stripeintegration.infrastructure.service.stripe;

import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sir.stripeintegration.core.application.interfaces.service.IWebhookHandler;
import org.sir.stripeintegration.core.domain.TransactionEntity;
import org.sir.stripeintegration.core.shared.exceptions.CustomException;
import org.sir.stripeintegration.infrastructure.persistance.repository.TransactionRepository;
import org.sir.stripeintegration.infrastructure.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class WebhookHandlerService implements IWebhookHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private final TransactionRepository transactionRepository;

    @Override
    public Mono<Void> handlePaymentIntentEvent(StripeObject stripeObject, String eventName) {
        PaymentIntent paymentIntent = (PaymentIntent) stripeObject;

        TransactionEntity transactionEntity = TransactionEntity.builder()
                .id(UUID.randomUUID())
                .customerId(paymentIntent.getCustomer())
                .amount(paymentIntent.getAmount())
                .paymentIntentId(paymentIntent.getId())
                .isSuccess(eventName.equals("payment_intent.succeeded"))
                .isNewEntry(true)
                .build();
        try {
            return transactionRepository.save(transactionEntity)
                    .flatMap(transactionEntity1 -> Mono.empty());
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new CustomException("Error occurred on transaction create");
        }
    }

    @Override
    public Mono<Void> handleInvoiceEvent(StripeObject stripeObject, String eventName) {
        return null;
    }

    @Override
    public Mono<Void> handleSubscriptionEvent(StripeObject stripeObject, String eventName) {
        return null;
    }
}

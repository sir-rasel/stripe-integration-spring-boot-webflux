package org.sir.stripeintegration.core.application.interfaces.service;

import com.stripe.model.StripeObject;
import reactor.core.publisher.Mono;

public interface IWebhookHandler {
    Mono<Void> handlePaymentIntentEvent(StripeObject stripeObject, String eventName);

    Mono<Void> handleInvoiceEvent(StripeObject stripeObject, String eventName);

    Mono<Void> handleSubscriptionEvent(StripeObject stripeObject, String eventName);
}

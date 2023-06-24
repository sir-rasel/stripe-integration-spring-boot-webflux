package org.sir.stripeintegration.host.controller;

import com.google.gson.JsonSyntaxException;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.StripeObject;
import com.stripe.net.Webhook;
import lombok.AllArgsConstructor;
import org.sir.stripeintegration.core.application.interfaces.service.IWebhookHandler;
import org.sir.stripeintegration.core.shared.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/api/webhook-handler")
public class WebhookCallHandleController {
    public final IWebhookHandler webhookHandler;

    @Autowired
    private Environment environment;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> handleWebhookEvent(HttpEntity<String> request) {
        Event event = validateStripeHeadersAndReturnEvent(
                request.getBody(), request.getHeaders().getFirst("Stripe-Signature"));

        StripeObject stripeObject = getStipeObjectFromWebhookRequest(event);

        switch (event.getType()) {
            case "payment_intent.succeeded":
            case "payment_intent.payment_failed":
                return webhookHandler.handlePaymentIntentEvent(stripeObject, event.getType());
            case "invoice.paid":
            case "invoice.payment_failed":
                return webhookHandler.handleInvoiceEvent(stripeObject, event.getType());
            case "customer.subscription.deleted":
            case "customer.subscription.updated":
                return webhookHandler.handleSubscriptionEvent(stripeObject, event.getType());
            default:
                return Mono.empty();
        }
    }

    private Event validateStripeHeadersAndReturnEvent(String payload, String headers) {
        String stripeWebhookKey = environment.getProperty("stripe.key.webhook");

        try {
            return Webhook.constructEvent(payload, headers, stripeWebhookKey);
        } catch (JsonSyntaxException e) {
            throw new CustomException("Invalid payload");
        } catch (SignatureVerificationException e) {
            throw new CustomException("Invalid Signature");
        }
    }

    private StripeObject getStipeObjectFromWebhookRequest(Event event) {
        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();

        if (dataObjectDeserializer.getObject().isPresent()) {
            return dataObjectDeserializer.getObject().get();
        } else {
            throw new CustomException("Deserialize error");
        }
    }
}

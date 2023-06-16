package org.sir.stripeintegration.core.shared.constant;

import lombok.Getter;

@Getter
public enum ErrorMessage {
    CUSTOMER_NOT_FOUND("Customer not found"),
    PAYMENT_METHOD_NOT_FOUND("Payment method not found"),
    PAYMENT_INTENT_NOT_FOUND("Payment intent not found"),
    PRODUCT_NOT_FOUND("Product not found");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}

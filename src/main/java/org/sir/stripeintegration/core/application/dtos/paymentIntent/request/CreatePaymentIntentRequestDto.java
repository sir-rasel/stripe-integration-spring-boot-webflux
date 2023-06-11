package org.sir.stripeintegration.core.application.dtos.paymentIntent.request;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CreatePaymentIntentRequestDto {
    @NonNull
    public String customerId;

    @NonNull
    @Min(50)
    public Integer amount;

    @NonNull
    public String currency = "usd";
}

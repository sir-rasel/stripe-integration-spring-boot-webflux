package org.sir.stripeintegration.core.application.dtos.paymentIntent.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentIntentRequestDto {
    @NotNull
    public String customerId;

    @NotNull
    @Min(50)
    public Integer amount;

    @NotNull
    public String currency = "usd";
}

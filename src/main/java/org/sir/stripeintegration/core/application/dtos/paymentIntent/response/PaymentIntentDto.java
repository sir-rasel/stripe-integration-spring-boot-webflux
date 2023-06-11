package org.sir.stripeintegration.core.application.dtos.paymentIntent.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentIntentDto {
    public String id;
    public int amount;
    public String status;
    public String paymentMethod;
    public String currency;
    public String customer;
}

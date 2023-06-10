package org.sir.stripeintegration.core.application.dtos.paymentMethod.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sir.stripeintegration.core.shared.dtoModels.BillingDetailsDto;
import org.sir.stripeintegration.core.shared.dtoModels.CardDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethodDto {
    public String id;
    public String customerId;
    public BillingDetailsDto billingDetails;
    public String type;
    public CardDto card;
}

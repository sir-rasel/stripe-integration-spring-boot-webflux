package org.sir.stripeintegration.core.application.dtos.paymentMethod.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.sir.stripeintegration.core.shared.dtoModels.AddressDto;

@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentMethodRequestDto {
    @NotNull
    public String customerId;

    @NotNull
    public String cardNumber;

    @NotNull
    public Integer expMonth;

    @NotNull
    public Integer expYear;

    @NotNull
    public String cvc;

    @NotNull
    public Boolean makeAsCustomerDefaultPaymentMethod = true;

    public AddressDto address;
}

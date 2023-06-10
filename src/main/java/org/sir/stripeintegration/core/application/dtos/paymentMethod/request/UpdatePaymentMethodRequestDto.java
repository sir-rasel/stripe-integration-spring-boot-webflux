package org.sir.stripeintegration.core.application.dtos.paymentMethod.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sir.stripeintegration.core.shared.dtoModels.AddressDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePaymentMethodRequestDto {
    @NotNull
    @NotEmpty
    @NotBlank
    public String id;

    @NotNull
    public String expMonth;

    @NotNull
    public String expYear;

    public AddressDto address;
}

package org.sir.stripeintegration.core.application.dtos.productPrice.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class UpdateProductPriceRequestDto {
    @NotNull
    @NotEmpty
    @NotBlank
    public String id;

    public boolean active = true;
    public String nickName;
}

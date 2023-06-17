package org.sir.stripeintegration.core.application.dtos.product.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sir.stripeintegration.core.application.dtos.product.ProductCommonDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequestDto extends ProductCommonDto {
    @NotNull
    @NotEmpty
    @NotBlank
    public String id;
    
    public String defaultPriceId;
}

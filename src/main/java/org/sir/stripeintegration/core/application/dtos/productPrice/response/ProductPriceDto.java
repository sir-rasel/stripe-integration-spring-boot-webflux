package org.sir.stripeintegration.core.application.dtos.productPrice.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.sir.stripeintegration.core.application.dtos.productPrice.ProductPriceCommonDto;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ProductPriceDto extends ProductPriceCommonDto {
    public String id;
    public String type;
}

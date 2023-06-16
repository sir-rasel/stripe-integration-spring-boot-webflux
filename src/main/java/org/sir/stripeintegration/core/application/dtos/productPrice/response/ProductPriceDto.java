package org.sir.stripeintegration.core.application.dtos.productPrice.response;

import lombok.*;
import org.sir.stripeintegration.core.application.dtos.productPrice.ProductPriceCommonDto;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductPriceDto extends ProductPriceCommonDto {
    public String id;
    public String type;
}

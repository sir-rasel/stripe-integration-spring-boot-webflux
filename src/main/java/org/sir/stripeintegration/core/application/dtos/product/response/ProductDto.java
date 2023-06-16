package org.sir.stripeintegration.core.application.dtos.product.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.sir.stripeintegration.core.application.dtos.product.ProductCommonDto;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ProductDto extends ProductCommonDto {
    public String id;
    public String defaultPriceId;
}

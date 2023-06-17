package org.sir.stripeintegration.core.application.dtos.productPrice;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.sir.stripeintegration.core.shared.dtoModels.RecurringDto;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ProductPriceCommonDto {
    public Boolean active = true;
    public String currency;
    public String nickName;
    public String productId;
    public Long unitAmount;
    public RecurringDto recurring;
}

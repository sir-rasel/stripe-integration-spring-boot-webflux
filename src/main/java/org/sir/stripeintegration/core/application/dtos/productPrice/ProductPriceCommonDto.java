package org.sir.stripeintegration.core.application.dtos.productPrice;

import org.sir.stripeintegration.core.shared.dtoModels.RecurringDto;

public class ProductPriceCommonDto {
    public boolean active;
    public String currency;
    public String nickname;
    public String productId;
    public int unitAmount;
    public RecurringDto recurring;
}

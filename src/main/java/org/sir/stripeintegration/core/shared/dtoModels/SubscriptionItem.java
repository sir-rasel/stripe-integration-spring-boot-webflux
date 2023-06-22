package org.sir.stripeintegration.core.shared.dtoModels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionItem {
    public String priceId;
    public Long quantity;
}

package org.sir.stripeintegration.core.application.dtos.subscription.request;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.sir.stripeintegration.core.shared.dtoModels.SubscriptionItem;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class UpdateSubscriptionDto {
    public List<SubscriptionItem> items;
    public Boolean cancelAtPeriodEnd = false;
    public String defaultPaymentMethodId;
    public String description;
    public String cancelAt;
}

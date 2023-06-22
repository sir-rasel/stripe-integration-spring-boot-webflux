package org.sir.stripeintegration.core.application.dtos.subscription.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sir.stripeintegration.core.shared.dtoModels.SubscriptionItem;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionDto {
    public String id;
    public Boolean cancelAtPeriodEnd;
    public String currency;
    public String currentPeriodEnd;
    public String currentPeriodStart;
    public String customerId;
    public String defaultPaymentMethod;
    public String description;
    public String status;
    public String cancelAt;
    public List<SubscriptionItem> items;
}

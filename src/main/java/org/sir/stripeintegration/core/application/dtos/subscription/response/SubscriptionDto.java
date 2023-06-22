package org.sir.stripeintegration.core.application.dtos.subscription.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sir.stripeintegration.core.shared.dtoModels.SubscriptionItemDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionDto {
    public String id;
    public Boolean cancelAtPeriodEnd;
    public String currency;
    public Long currentPeriodEnd;
    public Long currentPeriodStart;
    public String customerId;
    public String defaultPaymentMethodId;
    public String description;
    public String status;
    public Long cancelAt;
    public List<SubscriptionItemDto> items;
}

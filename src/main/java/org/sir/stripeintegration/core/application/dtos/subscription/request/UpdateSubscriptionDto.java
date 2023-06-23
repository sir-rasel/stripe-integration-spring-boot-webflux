package org.sir.stripeintegration.core.application.dtos.subscription.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.sir.stripeintegration.core.shared.dtoModels.SubscriptionItemDto;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class UpdateSubscriptionDto {
    @NotNull
    public String id;

    public List<SubscriptionItemDto> items = new ArrayList<>();
    public Boolean cancelAtPeriodEnd = false;
    public String defaultPaymentMethodId;
    public String description;
    public Long cancelAt;
}

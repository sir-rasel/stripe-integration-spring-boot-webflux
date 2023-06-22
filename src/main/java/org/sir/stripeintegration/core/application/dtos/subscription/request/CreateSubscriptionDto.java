package org.sir.stripeintegration.core.application.dtos.subscription.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.sir.stripeintegration.core.shared.dtoModels.SubscriptionItem;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class CreateSubscriptionDto {
    @NotNull
    @NotBlank
    @NotEmpty
    public String customerId;

    public List<SubscriptionItem> items;
    public Boolean cancelAtPeriodEnd = false;
    public String defaultPaymentMethodId;
    public String description;
    public String cancelAt;
}

package org.sir.stripeintegration.core.application.dtos.subscription.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.sir.stripeintegration.core.shared.dtoModels.SubscriptionItemDto;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class CreateSubscriptionDto {
    @NotNull
    @NotBlank
    @NotEmpty
    public String customerId;

    public List<SubscriptionItemDto> items = new ArrayList<>();
    public Boolean cancelAtPeriodEnd = false;
    public String defaultPaymentMethodId;
    public String description;
    public Long cancelAt;
    public String currency;
}

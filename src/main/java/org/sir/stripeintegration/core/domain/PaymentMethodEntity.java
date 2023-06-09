package org.sir.stripeintegration.core.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.sir.stripeintegration.core.shared.EntityAuditFields;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@EntityScan
public class PaymentMethodEntity extends EntityAuditFields {
    @Id
    public UUID id;

    @NotNull
    public String customerId;

    @NotNull
    public String paymentMethodId;
}

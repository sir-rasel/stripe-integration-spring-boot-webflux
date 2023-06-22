package org.sir.stripeintegration.core.domain;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.sir.stripeintegration.core.shared.EntityAuditFields;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

@EqualsAndHashCode(callSuper = true)
@Data
@EntityScan
public class SubscriptionEntity extends EntityAuditFields implements Persistable<String> {
    @Id
    public String id;

    @NotNull
    public String customerId;

    @Nullable
    public String currency;

    @NotNull
    public String status;

    @Nullable
    public String description;

    @Nullable
    public Long cancelAt;

    @Nullable
    public Long currentPeriodEnd;

    @Nullable
    public Long currentPeriodStart;

    @Transient
    private boolean isNewEntry;

    @Override
    public boolean isNew() {
        return isNewEntry;
    }
}

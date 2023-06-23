package org.sir.stripeintegration.core.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.sir.stripeintegration.core.shared.EntityAuditFields;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@EntityScan
@Builder
public class TransactionEntity extends EntityAuditFields implements Persistable<UUID> {
    @Id
    public UUID id;

    @NotNull
    public String customerId;

    @NotNull
    public String paymentIntentId;

    @NotNull
    public Long amount;

    @NotNull
    public Boolean isSuccess;

    @Transient
    private boolean isNewEntry;

    @Override
    public boolean isNew() {
        return isNewEntry;
    }
}

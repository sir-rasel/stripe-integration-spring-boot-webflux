package org.sir.stripeintegration.core.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.sir.stripeintegration.core.shared.EntityAuditFields;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

@EqualsAndHashCode(callSuper = true)
@Data
@EntityScan
public class PaymentIntentEntity extends EntityAuditFields implements Persistable<String> {
    @Id
    public String id;
    public int amount;
    public String status;
    public String paymentMethodId;
    public String currency;
    public String customerId;

    @Transient
    private boolean isNewEntry;

    @Override
    public boolean isNew() {
        return isNewEntry;
    }
}

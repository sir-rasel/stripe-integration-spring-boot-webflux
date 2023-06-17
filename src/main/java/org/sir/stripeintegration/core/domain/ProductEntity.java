package org.sir.stripeintegration.core.domain;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.sir.stripeintegration.core.shared.EntityAuditFields;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

@EqualsAndHashCode(callSuper = true)
@Data
@EntityScan
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity extends EntityAuditFields implements Persistable<String> {
    @Id
    public String id;

    @NotNull
    public String name;

    @Nullable
    public String description;

    @Nullable
    public String defaultPriceId;

    @Nullable
    public Boolean active;

    @Nullable
    public Boolean shippable;

    @Transient
    private boolean isNewEntry;

    @Override
    public boolean isNew() {
        return isNewEntry;
    }
}

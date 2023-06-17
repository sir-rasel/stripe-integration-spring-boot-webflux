package org.sir.stripeintegration.core.domain;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
public class ProductPriceEntity extends EntityAuditFields implements Persistable<String> {
    @Id
    public String id;

    @NotNull
    @NotBlank
    @NotEmpty
    public String productId;

    @NotNull
    public String type;

    @Nullable
    public String nickname;

    @Nullable
    public String currency;

    @Nullable
    public Boolean active;

    @NotNull
    public Integer unitAmount;

    @Transient
    private boolean isNewEntry;

    @Override
    public boolean isNew() {
        return isNewEntry;
    }
}

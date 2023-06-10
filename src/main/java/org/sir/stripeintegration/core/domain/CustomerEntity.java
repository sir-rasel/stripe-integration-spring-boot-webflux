package org.sir.stripeintegration.core.domain;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
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
public class CustomerEntity extends EntityAuditFields implements Persistable<String> {
    @Id
    public String id;

    @NotNull
    @Email
    public String email;

    @NotNull
    public String name;

    @Nullable
    public String phone;

    @Transient
    private boolean isNewEntry;

    @Override
    public boolean isNew() {
        return isNewEntry;
    }
}

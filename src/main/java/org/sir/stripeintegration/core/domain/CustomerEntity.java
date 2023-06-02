package org.sir.stripeintegration.core.domain;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.sir.stripeintegration.core.shared.EntityAuditFields;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;

@Data
@EntityScan
public class CustomerEntity extends EntityAuditFields {
    @NotNull
    @Id
    public String id;

    @NotNull
    @Email
    public String email;

    @NotNull
    public String name;

    @Nullable
    public String phone;
}

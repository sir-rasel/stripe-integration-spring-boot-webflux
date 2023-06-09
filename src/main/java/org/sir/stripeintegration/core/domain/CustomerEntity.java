package org.sir.stripeintegration.core.domain;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
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
public class CustomerEntity extends EntityAuditFields {
    @Id
    public UUID id;

    @NotNull
    public String customerId;

    @NotNull
    @Email
    public String email;

    @NotNull
    public String name;

    @Nullable
    public String phone;
}

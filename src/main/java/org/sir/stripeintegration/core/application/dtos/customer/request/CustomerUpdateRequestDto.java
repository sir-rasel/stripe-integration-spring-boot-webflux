package org.sir.stripeintegration.core.application.dtos.customer.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CustomerUpdateRequestDto {
    @NotNull
    @NotEmpty
    @NotBlank
    public String id;

    @Email
    @NotNull
    public String email;

    @NotNull
    public String name;

    @Nullable
    public String phone;
}

package org.sir.stripeintegration.core.application.dtos.loginSignup.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequestDto {
    @NotNull
    @NotEmpty
    @NotBlank
    @Email
    private String userEmail;

    @NotNull
    @NotEmpty
    @NotBlank
    private String password;
}

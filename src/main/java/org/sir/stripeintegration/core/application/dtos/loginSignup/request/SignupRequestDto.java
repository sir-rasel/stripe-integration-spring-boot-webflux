package org.sir.stripeintegration.core.application.dtos.loginSignup.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class SignupRequestDto {
    @NotBlank(message = "First name should not be empty")
    private String firstName;

    @NotBlank(message = "Last name should not be empty")
    private String lastName;

    @NotBlank(message = "Password should not be empty")
    private String password;

    @Email(message = "Invalid email address")
    private String email;

    @NotBlank(message = "Address should not be empty")
    private String address;

    @NotEmpty(message = "Roles should not be empty")
    private List<@NotBlank(message = "Role should not be empty") String> roles;
    private Boolean active = true;
}

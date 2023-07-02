package org.sir.stripeintegration.core.application.dtos.loginSignup.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDto {
    @NotNull
    @NotEmpty
    @NotBlank(message = "First name should not be empty")
    private String firstName;

    @NotNull
    @NotEmpty
    @NotBlank(message = "Last name should not be empty")
    private String lastName;

    @NotNull
    @NotEmpty
    @NotBlank(message = "Password should not be empty")
    private String password;

    @NotNull
    @NotEmpty
    @NotBlank
    @Email(message = "Invalid email address")
    private String email;

    @NotNull
    @NotEmpty
    @NotBlank(message = "Address should not be empty")
    private String address;

    @NotEmpty(message = "Roles should not be empty")
    private List<@NotBlank(message = "Role should not be empty") String> roles;
    
    private Boolean active = true;
}

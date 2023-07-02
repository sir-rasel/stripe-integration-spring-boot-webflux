package org.sir.stripeintegration.core.application.dtos.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    public UUID id;
    public String firstName;
    public String lastName;
    public String password;
    public String email;
    public String address;
    public List<@NotBlank(message = "Role should not be empty") String> roles;
    public Boolean active = true;
}

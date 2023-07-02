package org.sir.stripeintegration.core.application.dtos.loginSignup.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {
    private String token;

    @Override
    public String toString() {
        return "LoginResponse{" +
                "token='" + token + '\'' +
                '}';
    }
}


package org.sir.stripeintegration.core.application.dtos.loginSignup.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class LoginResponseDto {
    private String token;
    private String refreshToken;
    private Date tokenExpirationDate;
    private Date refreshTokenExpirationDate;

    @Override
    public String toString() {
        return "LoginResponse{" +
                "token='" + token + '\'' +
                "tokenExpirationDate='" + tokenExpirationDate + '\'' +
                "refreshToken='" + refreshToken + '\'' +
                "refreshTokenExpirationDate='" + refreshTokenExpirationDate + '\'' +
                '}';
    }
}


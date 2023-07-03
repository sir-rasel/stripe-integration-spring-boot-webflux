package org.sir.stripeintegration.core.application.interfaces.service;

import org.sir.stripeintegration.core.application.dtos.loginSignup.request.LoginRequestDto;
import org.sir.stripeintegration.core.application.dtos.loginSignup.request.RefreshTokenDto;
import org.sir.stripeintegration.core.application.dtos.loginSignup.request.SignupRequestDto;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface IUserService {
    Mono<ResponseEntity<?>> validateLoginRequestAndGetTokenResponse(LoginRequestDto request);

    Mono<ResponseEntity<?>> createUserOnSignup(SignupRequestDto userdata);

    Mono<ResponseEntity<?>> refreshTokenAndGetTokenResponse(RefreshTokenDto request);
}

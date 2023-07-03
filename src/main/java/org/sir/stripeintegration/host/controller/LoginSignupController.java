package org.sir.stripeintegration.host.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.sir.stripeintegration.core.application.dtos.loginSignup.request.LoginRequestDto;
import org.sir.stripeintegration.core.application.dtos.loginSignup.request.RefreshTokenDto;
import org.sir.stripeintegration.core.application.dtos.loginSignup.request.SignupRequestDto;
import org.sir.stripeintegration.core.application.interfaces.service.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/api")
public class LoginSignupController {
    private IUserService userService;

    @PostMapping("/login")
    public Mono<ResponseEntity<?>> login(@RequestBody @Valid LoginRequestDto request) {
        return userService.validateLoginRequestAndGetTokenResponse(request);
    }

    @PostMapping("/signup")
    public Mono<ResponseEntity<?>> createUser(@RequestBody @Valid SignupRequestDto user) {
        return userService.createUserOnSignup(user);
    }

    @PostMapping("/refresh-token")
    public Mono<ResponseEntity<?>> refreshToken(@RequestBody @Valid RefreshTokenDto request) {
        return userService.refreshTokenAndGetTokenResponse(request);
    }
}

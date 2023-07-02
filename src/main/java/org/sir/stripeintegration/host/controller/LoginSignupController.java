package org.sir.stripeintegration.host.controller;

import lombok.AllArgsConstructor;
import org.sir.stripeintegration.core.application.dtos.loginSignup.request.LoginRequestDto;
import org.sir.stripeintegration.core.application.dtos.loginSignup.request.SignupRequestDto;
import org.sir.stripeintegration.core.application.interfaces.service.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@Validated
public class LoginSignupController {
    private IUserService userService;

    @PostMapping("/login")
    public Mono<ResponseEntity<?>> login(@RequestBody LoginRequestDto request) {
        return userService.validateLoginRequestAndGetUser(request);
    }

    @PostMapping("/signup")
    public Mono<ResponseEntity<?>> createUser(@RequestBody SignupRequestDto user) {
        return userService.createUserOnSignup(user);
    }
}

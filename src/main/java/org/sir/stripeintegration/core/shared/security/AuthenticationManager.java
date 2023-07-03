package org.sir.stripeintegration.core.shared.security;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.sir.stripeintegration.core.shared.constant.Roles;
import org.sir.stripeintegration.core.shared.utility.JWTUtil;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {
    private JWTUtil jwtUtil;

    @Override
    @SuppressWarnings("unchecked")
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();

        String userEmail;
        try {
            userEmail = jwtUtil.getUserEmailFromToken(authToken);
        } catch (Exception e) {
            userEmail = null;
        }
        if (userEmail != null && jwtUtil.validateToken(authToken)) {
            Claims claims = jwtUtil.getAllClaimsFromToken(authToken);
            List<String> rolesMap = claims.get("role", List.class);
            List<Roles> roles = new ArrayList<>();
            for (String rolemap : rolesMap) {
                roles.add(Roles.valueOf(rolemap));
            }
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    userEmail,
                    null,
                    roles.stream().map(authority ->
                            new SimpleGrantedAuthority(authority.name())).collect(Collectors.toList())
            );
            return Mono.just(auth);
        } else {
            return Mono.empty();
        }
    }
}

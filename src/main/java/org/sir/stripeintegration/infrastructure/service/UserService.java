package org.sir.stripeintegration.infrastructure.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.sir.stripeintegration.core.application.dtos.loginSignup.request.LoginRequestDto;
import org.sir.stripeintegration.core.application.dtos.loginSignup.request.SignupRequestDto;
import org.sir.stripeintegration.core.application.dtos.loginSignup.response.LoginResponseDto;
import org.sir.stripeintegration.core.application.dtos.user.UserDto;
import org.sir.stripeintegration.core.application.interfaces.service.IUserService;
import org.sir.stripeintegration.core.domain.UserEntity;
import org.sir.stripeintegration.core.shared.security.CustomEncoder;
import org.sir.stripeintegration.core.shared.utility.JWTUtil;
import org.sir.stripeintegration.core.shared.utility.UtilService;
import org.sir.stripeintegration.infrastructure.persistance.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class UserService implements IUserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final ModelMapper mapper = new ModelMapper();

    private JWTUtil jwtUtil;
    private UtilService utilService;
    private CustomEncoder passwordEncoder;

    @Override
    public Mono<ResponseEntity<?>> validateLoginRequestAndGetUser(LoginRequestDto request) {
        return findUserByUserName(request.getUserEmail())
                .map(userDetails -> {
                    if (passwordEncoder.encode(request.getPassword()).equals(userDetails.getPassword())) {
                        return ResponseEntity.ok(LoginResponseDto.builder()
                                .token(jwtUtil.generateToken(userDetails))
                                .build());
                    } else {
                        logger.info("Password not matched");
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                    }
                })
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    private Mono<UserEntity> findUserByUserName(String userEmail) {
        Mono<UserEntity> user = userRepository.findByEmail(userEmail);
        return user.switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<ResponseEntity<?>> createUserOnSignup(SignupRequestDto userdata) {
        String message = utilService.validation(userdata);
        if (message.isEmpty()) {
            userdata.setPassword(passwordEncoder.encode(userdata.getPassword()));
            return Mono.just(ResponseEntity.ok(createUser(userdata)));
        } else {
            return Mono.just(ResponseEntity.badRequest().body(message));
        }
    }

    private UserDto createUser(SignupRequestDto userData) {
        UserEntity user = new UserEntity(
                UUID.randomUUID(),
                userData.getFirstName(),
                userData.getLastName(),
                userData.getPassword(),
                userData.getEmail(),
                userData.getAddress(),
                userData.getRoles(),
                userData.getActive());
        user.setNewEntry(true);

        return userRepository.save(user)
                .map(userEntity -> mapper.map(userEntity, UserDto.class))
                .block();
    }
}

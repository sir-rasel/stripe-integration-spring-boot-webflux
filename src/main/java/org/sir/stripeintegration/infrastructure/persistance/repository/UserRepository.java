package org.sir.stripeintegration.infrastructure.persistance.repository;

import org.sir.stripeintegration.core.domain.UserEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface UserRepository extends R2dbcRepository<UserEntity, UUID> {
    @Query("SELECT * FROM USER_ENTITY WHERE EMAIL = $1 LIMIT 1")
    Mono<UserEntity> findByEmail(String email);
}

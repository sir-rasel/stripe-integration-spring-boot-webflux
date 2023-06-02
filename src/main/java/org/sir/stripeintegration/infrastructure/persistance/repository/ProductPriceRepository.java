package org.sir.stripeintegration.infrastructure.persistance.repository;

import org.sir.stripeintegration.core.domain.CustomerEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductPriceRepository extends R2dbcRepository<CustomerEntity, String> {
}

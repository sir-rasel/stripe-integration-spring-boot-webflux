package org.sir.stripeintegration.core.application.interfaces.repository;

import org.sir.stripeintegration.core.domain.CustomerEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import java.util.UUID;

public interface IProductPriceRepository extends R2dbcRepository<CustomerEntity, UUID> {
}

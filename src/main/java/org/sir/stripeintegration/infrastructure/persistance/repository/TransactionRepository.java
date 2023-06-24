package org.sir.stripeintegration.infrastructure.persistance.repository;

import org.sir.stripeintegration.core.domain.TransactionEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface TransactionRepository extends R2dbcRepository<TransactionEntity, UUID> {
    @Query("SELECT * FROM TRANSACTION_ENTITY WHERE CUSTOMER_ID = $1 LIMIT $2 OFFSET $3")
    Flux<TransactionEntity> getTransactionPagingData(String customerId, Integer pageSize, Integer offset);
}

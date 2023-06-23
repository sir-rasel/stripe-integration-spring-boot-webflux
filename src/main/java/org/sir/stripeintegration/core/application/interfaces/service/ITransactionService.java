package org.sir.stripeintegration.core.application.interfaces.service;

import org.sir.stripeintegration.core.application.dtos.transaction.response.TransactionDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ITransactionService {
    Mono<TransactionDto> getTransaction(UUID id);

    Flux<TransactionDto> getCustomerAllTransaction(String customerId, Integer pageSize, Integer pageIndex);
}

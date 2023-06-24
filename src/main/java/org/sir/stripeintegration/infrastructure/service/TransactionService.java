package org.sir.stripeintegration.infrastructure.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.sir.stripeintegration.core.application.dtos.transaction.response.TransactionDto;
import org.sir.stripeintegration.core.application.interfaces.service.ITransactionService;
import org.sir.stripeintegration.core.shared.constant.ErrorMessage;
import org.sir.stripeintegration.core.shared.exceptions.CustomException;
import org.sir.stripeintegration.infrastructure.persistance.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class TransactionService implements ITransactionService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);
    private final TransactionRepository transactionRepository;

    private final ModelMapper mapper = new ModelMapper();

    @Override
    public Mono<TransactionDto> getTransaction(UUID id) {
        return transactionRepository.findById(id)
                .map(transactionEntity -> mapper.map(transactionEntity, TransactionDto.class))
                .switchIfEmpty(Mono.error(new CustomException(ErrorMessage.TRANSACTION_NOT_FOUND.getMessage())));
    }

    @Override
    public Flux<TransactionDto> getCustomerAllTransaction(String customerId, Integer pageSize, Integer pageIndex) {
        pageSize = pageSize == null ? 10 : pageSize;
        pageIndex = pageIndex == null ? 0 : pageIndex;

        return transactionRepository.getTransactionPagingData(customerId, pageSize, pageIndex * pageSize)
                .map(transactionEntity -> mapper.map(transactionEntity, TransactionDto.class));
    }
}

package org.sir.stripeintegration.infrastructure.service;

import org.sir.stripeintegration.core.application.interfaces.repository.ITransactionRepository;
import org.sir.stripeintegration.core.application.interfaces.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService implements ITransactionService {
    @Autowired
    private final ITransactionRepository transactionRepository;

    public TransactionService(ITransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
}

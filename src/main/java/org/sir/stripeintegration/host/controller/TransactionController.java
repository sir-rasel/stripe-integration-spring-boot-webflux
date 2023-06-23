package org.sir.stripeintegration.host.controller;

import lombok.AllArgsConstructor;
import org.sir.stripeintegration.core.application.dtos.transaction.response.TransactionDto;
import org.sir.stripeintegration.core.application.interfaces.service.ITransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    private final ITransactionService transactionService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<TransactionDto> getTransaction(@PathVariable UUID id) {
        return transactionService.getTransaction(id);
    }

    @GetMapping("/all/customer/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<TransactionDto> getCustomerAllTransaction(
            @PathVariable String customerId,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Integer pageIndex) {
        return transactionService.getCustomerAllTransaction(customerId, pageSize, pageIndex);
    }
}

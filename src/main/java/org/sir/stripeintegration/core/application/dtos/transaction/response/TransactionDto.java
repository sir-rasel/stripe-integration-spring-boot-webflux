package org.sir.stripeintegration.core.application.dtos.transaction.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    public UUID id;
    public String customerId;
    public String paymentIntentId;
    public Long amount;
    public Boolean isSuccess;
}

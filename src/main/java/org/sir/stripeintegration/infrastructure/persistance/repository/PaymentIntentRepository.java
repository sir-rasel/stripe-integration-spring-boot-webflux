package org.sir.stripeintegration.infrastructure.persistance.repository;

import org.sir.stripeintegration.core.application.interfaces.repository.IPaymentIntentRepository;
import org.springframework.stereotype.Repository;

@Repository
public abstract class PaymentIntentRepository implements IPaymentIntentRepository {
}

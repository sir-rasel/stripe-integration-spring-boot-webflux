package org.sir.stripeintegration.infrastructure.service;

import org.sir.stripeintegration.core.application.interfaces.repository.IPaymentIntentRepository;
import org.sir.stripeintegration.core.application.interfaces.service.IPaymentIntentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentIntentService implements IPaymentIntentService {
    @Autowired
    private final IPaymentIntentRepository paymentIntentRepository;

    public PaymentIntentService(IPaymentIntentRepository paymentIntentRepository) {
        this.paymentIntentRepository = paymentIntentRepository;
    }
}

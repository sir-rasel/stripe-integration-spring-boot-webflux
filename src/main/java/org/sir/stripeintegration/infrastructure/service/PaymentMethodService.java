package org.sir.stripeintegration.infrastructure.service;

import org.sir.stripeintegration.core.application.interfaces.repository.IPaymentMethodRepository;
import org.sir.stripeintegration.core.application.interfaces.service.IPaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentMethodService implements IPaymentMethodService {
    @Autowired
    private final IPaymentMethodRepository paymentMethodRepository;

    public PaymentMethodService(IPaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }
}

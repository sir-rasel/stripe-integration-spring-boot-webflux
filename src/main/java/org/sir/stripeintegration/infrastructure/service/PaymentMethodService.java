package org.sir.stripeintegration.infrastructure.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sir.stripeintegration.infrastructure.persistance.repository.PaymentMethodRepository;
import org.sir.stripeintegration.core.application.interfaces.service.IPaymentMethodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class PaymentMethodService implements IPaymentMethodService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentMethodService.class);
    private final PaymentMethodRepository paymentMethodRepository;

}

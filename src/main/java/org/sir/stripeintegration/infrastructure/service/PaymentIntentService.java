package org.sir.stripeintegration.infrastructure.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.sir.stripeintegration.infrastructure.persistance.repository.PaymentIntentRepository;
import org.sir.stripeintegration.core.application.interfaces.service.IPaymentIntentService;
import org.sir.stripeintegration.infrastructure.service.stripe.StripeRootService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class PaymentIntentService implements IPaymentIntentService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentIntentService.class);
    private final PaymentIntentRepository paymentIntentRepository;

    private final StripeRootService stripeRootService;
    private final ModelMapper mapper = new ModelMapper();

}

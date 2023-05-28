package org.sir.stripeintegration.host.controller;

import org.sir.stripeintegration.core.application.interfaces.service.IPaymentIntentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment-intent")
public class PaymentIntentController {
    @Autowired
    private final IPaymentIntentService paymentIntentService;

    public PaymentIntentController(IPaymentIntentService paymentIntentService) {
        this.paymentIntentService = paymentIntentService;
    }
}

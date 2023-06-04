package org.sir.stripeintegration.host.controller;

import lombok.AllArgsConstructor;
import org.sir.stripeintegration.core.application.interfaces.service.IPaymentIntentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/payment-intent")
public class PaymentIntentController {
    private final IPaymentIntentService paymentIntentService;

}

package org.sir.stripeintegration.host.controller;

import lombok.AllArgsConstructor;
import org.sir.stripeintegration.core.application.interfaces.service.ISubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {
    private final ISubscriptionService subscriptionService;

}

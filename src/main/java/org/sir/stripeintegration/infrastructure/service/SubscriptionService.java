package org.sir.stripeintegration.infrastructure.service;

import org.sir.stripeintegration.core.application.interfaces.repository.ISubscriptionRepository;
import org.sir.stripeintegration.core.application.interfaces.service.ISubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService implements ISubscriptionService {
    @Autowired
    private final ISubscriptionRepository subscriptionRepository;

    public SubscriptionService(ISubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }
}

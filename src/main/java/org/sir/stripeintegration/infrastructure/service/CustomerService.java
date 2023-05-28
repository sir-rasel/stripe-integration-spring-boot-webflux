package org.sir.stripeintegration.infrastructure.service;

import org.sir.stripeintegration.core.application.interfaces.repository.ICustomerRepository;
import org.sir.stripeintegration.core.application.interfaces.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements ICustomerService {
    @Autowired
    private final ICustomerRepository customerRepository;

    public CustomerService(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
}

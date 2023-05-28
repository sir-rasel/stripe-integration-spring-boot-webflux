package org.sir.stripeintegration.infrastructure.service;

import org.sir.stripeintegration.core.application.interfaces.repository.IProductRepository;
import org.sir.stripeintegration.core.application.interfaces.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements IProductService {
    @Autowired
    private final IProductRepository productRepository;

    public ProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }
}

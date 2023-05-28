package org.sir.stripeintegration.infrastructure.service;

import org.sir.stripeintegration.core.application.interfaces.repository.IProductPriceRepository;
import org.sir.stripeintegration.core.application.interfaces.service.IProductPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductPriceService implements IProductPriceService {
    @Autowired
    private final IProductPriceRepository productPriceRepository;

    public ProductPriceService(IProductPriceRepository productPriceRepository) {
        this.productPriceRepository = productPriceRepository;
    }
}

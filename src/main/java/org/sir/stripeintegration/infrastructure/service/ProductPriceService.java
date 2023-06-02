package org.sir.stripeintegration.infrastructure.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sir.stripeintegration.infrastructure.persistance.repository.ProductPriceRepository;
import org.sir.stripeintegration.core.application.interfaces.service.IProductPriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ProductPriceService implements IProductPriceService {
    private static final Logger logger = LoggerFactory.getLogger(ProductPriceService.class);
    private final ProductPriceRepository productPriceRepository;

}

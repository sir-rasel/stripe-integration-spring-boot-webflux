package org.sir.stripeintegration.host.controller;

import org.sir.stripeintegration.core.application.interfaces.service.IProductPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product-price")
public class ProductPriceController {
    @Autowired
    private final IProductPriceService productPriceService;

    public ProductPriceController(IProductPriceService productPriceService) {
        this.productPriceService = productPriceService;
    }
}

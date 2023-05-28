package org.sir.stripeintegration.host.controller;

import org.sir.stripeintegration.core.application.interfaces.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }
}

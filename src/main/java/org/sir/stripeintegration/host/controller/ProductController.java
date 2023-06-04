package org.sir.stripeintegration.host.controller;

import lombok.AllArgsConstructor;
import org.sir.stripeintegration.core.application.interfaces.service.IProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final IProductService productService;

}

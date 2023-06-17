package org.sir.stripeintegration.host.controller;

import lombok.AllArgsConstructor;
import org.sir.stripeintegration.core.application.dtos.productPrice.request.CreateProductPriceRequestDto;
import org.sir.stripeintegration.core.application.dtos.productPrice.request.UpdateProductPriceRequestDto;
import org.sir.stripeintegration.core.application.dtos.productPrice.response.ProductPriceDto;
import org.sir.stripeintegration.core.application.interfaces.service.IProductPriceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/api/product-price")
public class ProductPriceController {
    private final IProductPriceService productPriceService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ProductPriceDto> getProductPrice(@PathVariable String id) {
        return productPriceService.getProductPrice(id);
    }

    @GetMapping("/all/product/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<ProductPriceDto> getProductAllPrices(
            @PathVariable String productId,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Long limit,
            @RequestParam(required = false) String startingAfter,
            @RequestParam(required = false) String endingBefore) {
        return productPriceService.getProductAllPrices(productId, active, type, limit, startingAfter, endingBefore);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductPriceDto> addProductPrice(
            @RequestBody CreateProductPriceRequestDto requestDto) {
        return productPriceService.addProductPrice(requestDto);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ProductPriceDto> updateProductPrice(
            @RequestBody UpdateProductPriceRequestDto requestDto) {
        return productPriceService.updateProductPrice(requestDto);
    }
}

package org.sir.stripeintegration.host.controller;

import lombok.AllArgsConstructor;
import org.sir.stripeintegration.core.application.dtos.product.request.CreateProductRequestDto;
import org.sir.stripeintegration.core.application.dtos.product.request.UpdateProductRequestDto;
import org.sir.stripeintegration.core.application.dtos.product.response.ProductDto;
import org.sir.stripeintegration.core.application.interfaces.service.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final IProductService productService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ProductDto> getProduct(@PathVariable String id) {
        return productService.getProduct(id);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Flux<ProductDto> getAllProduct(
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) Boolean shippable,
            @RequestParam(required = false) Long limit,
            @RequestParam(required = false) String startingAfter,
            @RequestParam(required = false) String endingBefore) {
        return productService.getAllProduct(active, shippable, limit, startingAfter, endingBefore);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductDto> addProduct(@RequestBody CreateProductRequestDto requestDto) {
        return productService.addProduct(requestDto);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ProductDto> updateProduct(@RequestBody UpdateProductRequestDto requestDto) {
        return productService.updateProduct(requestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteProduct(@PathVariable String id) {
        return productService.deleteProduct(id);
    }
}

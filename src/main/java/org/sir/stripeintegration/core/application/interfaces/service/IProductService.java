package org.sir.stripeintegration.core.application.interfaces.service;

import org.sir.stripeintegration.core.application.dtos.product.request.CreateProductRequestDto;
import org.sir.stripeintegration.core.application.dtos.product.request.UpdateProductRequestDto;
import org.sir.stripeintegration.core.application.dtos.product.response.ProductDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductService {
    Mono<ProductDto> getProduct(String id);

    Flux<ProductDto> getAllProduct(
            Boolean active, Boolean shippable, Long limit, String startingAfter, String endingBefore);

    Mono<ProductDto> addProduct(CreateProductRequestDto requestDto);

    Mono<ProductDto> updateProduct(UpdateProductRequestDto requestDto);

    Mono<Void> deleteProduct(String id);
}

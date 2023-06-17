package org.sir.stripeintegration.core.application.interfaces.service;

import org.sir.stripeintegration.core.application.dtos.productPrice.request.CreateProductPriceRequestDto;
import org.sir.stripeintegration.core.application.dtos.productPrice.request.UpdateProductPriceRequestDto;
import org.sir.stripeintegration.core.application.dtos.productPrice.response.ProductPriceDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductPriceService {
    Mono<ProductPriceDto> getProductPrice(String id);

    Flux<ProductPriceDto> getProductAllPrices(
            String productId, Boolean active, String type, Long limit, String startingAfter, String endingBefore);

    Mono<ProductPriceDto> addProductPrice(CreateProductPriceRequestDto requestDto);

    Mono<ProductPriceDto> updateProductPrice(UpdateProductPriceRequestDto requestDto);
}

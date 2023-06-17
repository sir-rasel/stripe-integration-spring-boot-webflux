package org.sir.stripeintegration.infrastructure.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.sir.stripeintegration.core.application.dtos.productPrice.request.CreateProductPriceRequestDto;
import org.sir.stripeintegration.core.application.dtos.productPrice.request.UpdateProductPriceRequestDto;
import org.sir.stripeintegration.core.application.dtos.productPrice.response.ProductPriceDto;
import org.sir.stripeintegration.core.application.interfaces.service.IProductPriceService;
import org.sir.stripeintegration.infrastructure.persistance.repository.ProductPriceRepository;
import org.sir.stripeintegration.infrastructure.service.stripe.StripeRootService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class ProductPriceService implements IProductPriceService {
    private static final Logger logger = LoggerFactory.getLogger(ProductPriceService.class);
    private final ProductPriceRepository productPriceRepository;

    private final StripeRootService stripeRootService;
    private final ModelMapper mapper = new ModelMapper();

    @Override
    public Mono<ProductPriceDto> getProductPrice(String id) {
        return null;
    }

    @Override
    public Flux<ProductPriceDto> getProductAllPrices(String productId, Boolean active, String type, Long limit, String startingAfter, String endingBefore) {
        return null;
    }

    @Override
    public Mono<ProductPriceDto> addProductPrice(CreateProductPriceRequestDto requestDto) {
        return null;
    }

    @Override
    public Mono<ProductPriceDto> updateProductPrice(UpdateProductPriceRequestDto requestDto) {
        return null;
    }
}

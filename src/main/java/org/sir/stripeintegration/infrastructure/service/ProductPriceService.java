package org.sir.stripeintegration.infrastructure.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sir.stripeintegration.core.application.dtos.productPrice.request.CreateProductPriceRequestDto;
import org.sir.stripeintegration.core.application.dtos.productPrice.request.UpdateProductPriceRequestDto;
import org.sir.stripeintegration.core.application.dtos.productPrice.response.ProductPriceDto;
import org.sir.stripeintegration.core.application.interfaces.service.IProductPriceService;
import org.sir.stripeintegration.core.domain.ProductPriceEntity;
import org.sir.stripeintegration.core.shared.constant.ErrorMessage;
import org.sir.stripeintegration.core.shared.exceptions.CustomException;
import org.sir.stripeintegration.infrastructure.persistance.repository.ProductPriceRepository;
import org.sir.stripeintegration.infrastructure.service.stripe.StripeRootService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ProductPriceService implements IProductPriceService {
    private static final Logger logger = LoggerFactory.getLogger(ProductPriceService.class);
    private final ProductPriceRepository productPriceRepository;

    private final StripeRootService stripeRootService;

    @Override
    public Mono<ProductPriceDto> getProductPrice(String id) {
        return productPriceRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(ErrorMessage.PRODUCT_PRICE_NOT_FOUND.getMessage())))
                .map(productPriceEntity -> stripeRootService.getPriceById(id));
    }

    @Override
    public Flux<ProductPriceDto> getProductAllPrices(
            String productId, Boolean active, String type, Long limit, String startingAfter, String endingBefore) {
        List<ProductPriceDto> productPriceDtos = stripeRootService.getProductAllPrices(
                productId, active, type, limit, startingAfter, endingBefore);
        return Mono.just(productPriceDtos).flatMapIterable(list -> list);
    }

    @Override
    public Mono<ProductPriceDto> addProductPrice(CreateProductPriceRequestDto requestDto) {
        try {
            ProductPriceDto productPriceDto = stripeRootService.createPrice(requestDto);
            Mono<ProductPriceEntity> product = saveProductPriceEntity(productPriceDto);

            return product.map(productEntity -> productPriceDto);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new CustomException("Error occurred on price create");
        }
    }

    private Mono<ProductPriceEntity> saveProductPriceEntity(ProductPriceDto productPriceDto) {
        ProductPriceEntity productPrice = getEntityFromDto(productPriceDto);

        return productPriceRepository.save(productPrice);
    }

    private ProductPriceEntity getEntityFromDto(ProductPriceDto dto) {
        return new ProductPriceEntity(dto.id, dto.productId, dto.type, dto.nickName, dto.currency,
                dto.active, dto.unitAmount, true);
    }

    @Override
    public Mono<ProductPriceDto> updateProductPrice(UpdateProductPriceRequestDto requestDto) {
        try {
            return updateProductPriceEntity(requestDto)
                    .switchIfEmpty(Mono.error(new CustomException(ErrorMessage.PRODUCT_PRICE_NOT_FOUND.getMessage())))
                    .map(productPriceEntity -> stripeRootService.updatePrice(requestDto));
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new CustomException("Error occurred on price update");
        }
    }

    private Mono<ProductPriceEntity> updateProductPriceEntity(UpdateProductPriceRequestDto requestDto) {
        Mono<ProductPriceEntity> productPrice = productPriceRepository.findById(requestDto.id);
        return productPrice.flatMap(productPriceEntity -> {
            productPriceEntity.setActive(requestDto.active);
            productPriceEntity.setNickName(requestDto.nickName);
            productPriceEntity.setNewEntry(false);

            return productPriceRepository.save(productPriceEntity);
        });
    }
}

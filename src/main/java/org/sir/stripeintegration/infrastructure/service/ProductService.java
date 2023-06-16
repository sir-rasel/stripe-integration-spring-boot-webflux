package org.sir.stripeintegration.infrastructure.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.sir.stripeintegration.core.application.dtos.customer.response.CustomerDto;
import org.sir.stripeintegration.core.application.dtos.product.request.CreateProductRequestDto;
import org.sir.stripeintegration.core.application.dtos.product.request.UpdateProductRequestDto;
import org.sir.stripeintegration.core.application.dtos.product.response.ProductDto;
import org.sir.stripeintegration.core.application.interfaces.service.IProductService;
import org.sir.stripeintegration.core.domain.ProductEntity;
import org.sir.stripeintegration.core.shared.constant.ErrorMessage;
import org.sir.stripeintegration.core.shared.exceptions.CustomException;
import org.sir.stripeintegration.infrastructure.persistance.repository.ProductRepository;
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
public class ProductService implements IProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;

    private final StripeRootService stripeRootService;
    private final ModelMapper mapper = new ModelMapper();

    @Override
    public Mono<ProductDto> getProduct(String id) {
        return productRepository.findById(id)
                .map(productEntity -> stripeRootService.getProductById(id))
                .switchIfEmpty(Mono.error(new CustomException(ErrorMessage.PRODUCT_NOT_FOUND.getMessage())));
    }

    @Override
    public Flux<ProductDto> getAllProduct(
            Boolean active, Boolean shippable, Long limit, String startingAfter, String endingBefore) {
        List<ProductDto> productDtos = stripeRootService.getAllProducts(
                active, shippable, limit, startingAfter, endingBefore);

        return Mono.just(productDtos).flatMapIterable(list -> list);
    }

    @Override
    public Mono<ProductDto> addProduct(CreateProductRequestDto requestDto) {
        try {
            ProductDto productDto = stripeRootService.createProduct(requestDto);
            Mono<ProductEntity> product = saveProductEntity(productDto);

            return product.map(productEntity -> productDto);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new CustomException("Error occurred on product create");
        }
    }

    private Mono<ProductEntity> saveProductEntity(ProductDto productDto) {
        ProductEntity product = mapper.map(productDto, ProductEntity.class);
        product.setNewEntry(true);

        return productRepository.save(product);
    }

    @Override
    public Mono<ProductDto> updateProduct(UpdateProductRequestDto requestDto) {
        try {
            return updateProductEntity(requestDto)
                    .switchIfEmpty(Mono.error(new CustomException(ErrorMessage.PRODUCT_NOT_FOUND.getMessage())))
                    .map(productEntity -> stripeRootService.updateProduct(requestDto));
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new CustomException("Error occurred on customer update");
        }
    }

    private Mono<ProductEntity> updateProductEntity(UpdateProductRequestDto requestDto) {
        Mono<ProductEntity> product = productRepository.findById(requestDto.id);
        return product.flatMap(productEntity -> {
            productEntity.setName(requestDto.name);
            productEntity.setDescription(requestDto.description);
            productEntity.setActive(requestDto.active);
            productEntity.setShippable(requestDto.shippable);
            productEntity.setDefaultPriceId(requestDto.defaultPriceId);

            return productRepository.save(productEntity);
        });
    }

    @Override
    public Mono<Void> deleteProduct(String id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(ErrorMessage.PRODUCT_NOT_FOUND.getMessage())))
                .flatMap(productEntity -> {
                    stripeRootService.deleteProductById(id);
                    return deleteProductEntity(id);
                });
    }

    private Mono<Void> deleteProductEntity(String id) {
        return productRepository.deleteById(id);
    }
}

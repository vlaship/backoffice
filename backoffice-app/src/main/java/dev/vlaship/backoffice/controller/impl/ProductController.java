package dev.vlaship.backoffice.controller.impl;

import dev.vlaship.backoffice.api.ProductApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import dev.vlaship.backoffice.controller.AbstractController;
import dev.vlaship.backoffice.dto.PriceDto;
import dev.vlaship.backoffice.dto.ProductDto;
import dev.vlaship.backoffice.dto.ProductCreationDto;
import dev.vlaship.backoffice.facade.impl.ProductFacade;
import dev.vlaship.backoffice.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
public class ProductController extends AbstractController<Product, ProductDto> implements ProductApi {

    private final ProductFacade facade;

    @Override
    public ResponseEntity<ProductDto> create(ProductCreationDto productCreationDto) {
        var dto = facade.create(productCreationDto);
        return ResponseEntity.created(URI.create("/product/" + dto.id())).body(dto);
    }

    @Override
    public ResponseEntity<ProductDto> add(
            Long productId,
            PriceDto priceDto
    ) {
        return ResponseEntity.ok(facade.add(priceDto, productId));
    }

    @Override
    public ResponseEntity<ProductDto> add(
            Long productId,
            Long categoryId
    ) {
        return ResponseEntity.ok(facade.add(categoryId, productId));
    }

    @Override
    public ResponseEntity<ProductDto> removeCategory(
            Long productId,
            Long categoryId
    ) {
        return ResponseEntity.ok(facade.removeCategory(categoryId, productId));
    }

    @Override
    public ResponseEntity<ProductDto> removePrice(
            Long productId,
            Long priceId
    ) {
        return ResponseEntity.ok(facade.removePrice(priceId, productId));
    }

    @Override
    public ResponseEntity<List<ProductDto>> findAllByPrice(
            PriceDto priceDto,
            Pageable pageable
    ) {
        return ResponseEntity.ok(facade.findAll(priceDto, pageable));
    }

    @Override
    public ResponseEntity<List<ProductDto>> findAllByCategory(
            Long categoryId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(facade.findAll(categoryId, pageable));
    }

    @Override
    public ResponseEntity<List<ProductDto>> findAllName(
            String name,
            Pageable pageable
    ) {
        return ResponseEntity.ok(facade.findAll(name, pageable));
    }

    public ProductController(final ProductFacade facade) {
        super(facade);
        this.facade = facade;
    }

}

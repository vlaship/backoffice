package dev.vlaship.backoffice.controller;

import dev.vlaship.backoffice.api.ProductApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import dev.vlaship.backoffice.dto.PriceDto;
import dev.vlaship.backoffice.dto.ProductDto;
import dev.vlaship.backoffice.dto.ProductCreationDto;
import dev.vlaship.backoffice.facade.ProductFacade;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController implements ProductApi {

    private final ProductFacade facade;

    @Override
    public ResponseEntity<ProductDto> addCategoryToProduct(Long productId, Long categoryId) {
        return ResponseEntity.ok(facade.add(categoryId, productId));
    }

    @Override
    public ResponseEntity<ProductDto> addPriceToProduct(Long productId, PriceDto priceDto) {
        return ResponseEntity.ok(facade.add(priceDto, productId));
    }

    @Override
    public ResponseEntity<ProductDto> createProduct(ProductCreationDto productCreationDto) {
        var dto = facade.create(productCreationDto);
        return ResponseEntity.created(URI.create("/product/" + dto.getId())).body(dto);
    }

    @Override
    public ResponseEntity<Void> deleteProduct(Long id) {
        facade.delete(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<ProductDto> getProductById(Long id) {
        return ResponseEntity.ok(facade.find(id));
    }

    @Override
    public ResponseEntity<List<ProductDto>> getProducts(Pageable pageable) {
        return ResponseEntity.ok(facade.findAll(pageable));
    }

    @Override
    public ResponseEntity<List<ProductDto>> getProductsByCategoryId(Long categoryId, Pageable pageable) {
        return ResponseEntity.ok(facade.findAll(categoryId, pageable));
    }

    @Override
    public ResponseEntity<List<ProductDto>> getProductsByName(String name, Pageable pageable) {
        return ResponseEntity.ok(facade.findAll(name, pageable));
    }

    @Override
    public ResponseEntity<List<ProductDto>> getProductsByPrice(PriceDto priceDto, Pageable pageable) {
        return null;
    }

    @Override
    public ResponseEntity<ProductDto> removeCategoryFromProduct(Long productId, Long categoryId) {
        return ResponseEntity.ok(facade.removeCategory(categoryId, productId));
    }

    @Override
    public ResponseEntity<ProductDto> removePriceFromProduct(Long productId, Long priceId) {
        return ResponseEntity.ok(facade.removePrice(priceId, productId));
    }

    @Override
    public ResponseEntity<ProductDto> updateProduct(ProductDto productDto) {
        return ResponseEntity.accepted().body(facade.update(productDto));
    }
}

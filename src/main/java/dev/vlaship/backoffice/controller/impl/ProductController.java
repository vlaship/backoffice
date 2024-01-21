package dev.vlaship.backoffice.controller.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import dev.vlaship.backoffice.controller.AbstractController;
import dev.vlaship.backoffice.dto.PriceDto;
import dev.vlaship.backoffice.dto.ProductDto;
import dev.vlaship.backoffice.dto.ProductCreationDto;
import dev.vlaship.backoffice.facade.impl.ProductFacade;
import dev.vlaship.backoffice.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@Tag(name = "product")
@RequestMapping("/api/product")
public class ProductController extends AbstractController<Product, ProductDto> {

    private final ProductFacade facade;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductDto> create(@Valid final @RequestBody ProductCreationDto productCreationDto) {
        var dto = facade.create(productCreationDto);
        return ResponseEntity.created(URI.create("/product/" + dto.id())).body(dto);
    }

    @PutMapping("/{productId}/add/price")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductDto> add(
            final @PathVariable("productId") Long productId,
            @Valid final @RequestBody PriceDto priceDto
    ) {
        return ResponseEntity.accepted().body(facade.add(priceDto, productId));
    }

    @PutMapping("/{productId}/add/category/{categoryId}")
    public ResponseEntity<ProductDto> add(
            final @PathVariable("productId") Long productId,
            final @PathVariable("categoryId") Long categoryId
    ) {
        return ResponseEntity.accepted().body(facade.add(categoryId, productId));
    }

    @PutMapping("/{productId}/remove/category/{categoryId}")
    public ResponseEntity<ProductDto> removeCategory(
            final @PathVariable("productId") Long productId,
            final @PathVariable("categoryId") Long categoryId
    ) {
        return ResponseEntity.accepted().body(facade.removeCategory(categoryId, productId));
    }

    @PutMapping("/{productId}/remove/price/{priceId}")
    public ResponseEntity<ProductDto> removePrice(
            final @PathVariable("productId") Long productId,
            final @PathVariable("priceId") Long priceId
    ) {
        return ResponseEntity.accepted().body(facade.removePrice(priceId, productId));
    }

    @GetMapping("/price")
    public ResponseEntity<List<ProductDto>> findAllByPrice(
            @Valid final @RequestBody PriceDto priceDto,
            final Pageable pageable
    ) {
        return ResponseEntity.ok(facade.findAll(priceDto, pageable));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDto>> findAllByCategory(
            final @PathVariable("categoryId") Long categoryId,
            final Pageable pageable
    ) {
        return ResponseEntity.ok(facade.findAll(categoryId, pageable));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<ProductDto>> findAllName(
            final @PathVariable("name") String name,
            final Pageable pageable
    ) {
        return ResponseEntity.ok(facade.findAll(name, pageable));
    }

    public ProductController(final ProductFacade facade) {
        super(facade);
        this.facade = facade;
    }

}

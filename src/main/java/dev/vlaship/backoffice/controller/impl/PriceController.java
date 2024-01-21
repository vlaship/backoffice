package dev.vlaship.backoffice.controller.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import dev.vlaship.backoffice.controller.AbstractController;
import dev.vlaship.backoffice.dto.BetweenPrice;
import dev.vlaship.backoffice.dto.PriceDto;
import dev.vlaship.backoffice.dto.ProductDto;
import dev.vlaship.backoffice.facade.impl.PriceFacade;
import dev.vlaship.backoffice.model.Price;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@Tag(name = "price")
@RequestMapping("/api/price")
public class PriceController extends AbstractController<Price, PriceDto> {

    private final PriceFacade priceFacade;

    @GetMapping("/between/{currency}/{from}/{to}")
    public ResponseEntity<List<PriceDto>> findAllBetween(
            final @PathVariable("currency") String currency,
            final @PathVariable("from") BigDecimal from,
            final @PathVariable("to") BigDecimal to,
            final Pageable pageable
    ) {
        return ResponseEntity.ok(priceFacade.findAll(currency, from, to, pageable));
    }

    @GetMapping("/between")
    public ResponseEntity<List<PriceDto>> findAllBetween(
            @Valid final @RequestBody BetweenPrice betweenPrice,
            final Pageable pageable
    ) {
        return ResponseEntity.ok(priceFacade.findAll(betweenPrice, pageable));
    }

    @GetMapping("/currency/{currency}")
    public ResponseEntity<List<PriceDto>> findAllByCurrency(
            final @PathVariable("currency") String currency,
            final Pageable pageable
    ) {
        return ResponseEntity.ok(priceFacade.findAll(currency, pageable));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<PriceDto>> findAllByProduct(
            final @PathVariable("productId") Long productId,
            final Pageable pageable
    ) {
        return ResponseEntity.ok(priceFacade.findAll(productId, pageable));
    }

    @GetMapping("/product")
    public ResponseEntity<List<PriceDto>> findAllByProduct(
            @Valid final @RequestBody ProductDto productDto,
            final Pageable pageable
    ) {
        return ResponseEntity.ok(priceFacade.findAll(productDto.id(), pageable));
    }

    public PriceController(final PriceFacade priceFacade) {
        super(priceFacade);
        this.priceFacade = priceFacade;
    }

}

package dev.vlaship.backoffice.controller.impl;

import dev.vlaship.backoffice.api.PriceApi;
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

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
public class PriceController extends AbstractController<Price, PriceDto> implements PriceApi {

    private final PriceFacade priceFacade;

    @Override
    public ResponseEntity<List<PriceDto>> findAllBetween(
            String currency,
            BigDecimal from,
            BigDecimal to,
            Pageable pageable
    ) {
        return ResponseEntity.ok(priceFacade.findAll(currency, from, to, pageable));
    }

    @Override
    public ResponseEntity<List<PriceDto>> findAllBetween(
            BetweenPrice betweenPrice,
            Pageable pageable
    ) {
        return ResponseEntity.ok(priceFacade.findAll(betweenPrice, pageable));
    }

    @Override
    public ResponseEntity<List<PriceDto>> findAllByCurrency(
            String currency,
            Pageable pageable
    ) {
        return ResponseEntity.ok(priceFacade.findAll(currency, pageable));
    }

    @Override
    public ResponseEntity<List<PriceDto>> findAllByProduct(
            Long productId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(priceFacade.findAll(productId, pageable));
    }

    @Override
    public ResponseEntity<List<PriceDto>> findAllByProduct(
            ProductDto productDto,
            Pageable pageable
    ) {
        return ResponseEntity.ok(priceFacade.findAll(productDto.id(), pageable));
    }

    public PriceController(final PriceFacade priceFacade) {
        super(priceFacade);
        this.priceFacade = priceFacade;
    }

}

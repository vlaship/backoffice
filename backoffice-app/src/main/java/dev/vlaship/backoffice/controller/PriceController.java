package dev.vlaship.backoffice.controller;

import dev.vlaship.backoffice.api.PriceApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import dev.vlaship.backoffice.dto.BetweenPrice;
import dev.vlaship.backoffice.dto.PriceDto;
import dev.vlaship.backoffice.dto.ProductDto;
import dev.vlaship.backoffice.facade.impl.PriceFacade;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PriceController implements PriceApi {

    private final PriceFacade facade;

    @Override
    public ResponseEntity<List<PriceDto>> findAllBetween(
            String currency,
            BigDecimal from,
            BigDecimal to,
            Pageable pageable
    ) {
        return ResponseEntity.ok(facade.findAll(currency, from, to, pageable));
    }

    @Override
    public ResponseEntity<List<PriceDto>> findAllBetween(
            BetweenPrice betweenPrice,
            Pageable pageable
    ) {
        return ResponseEntity.ok(facade.findAll(betweenPrice, pageable));
    }

    @Override
    public ResponseEntity<List<PriceDto>> findAllByCurrency(
            String currency,
            Pageable pageable
    ) {
        return ResponseEntity.ok(facade.findAll(currency, pageable));
    }

    @Override
    public ResponseEntity<List<PriceDto>> findAllByProduct(
            Long productId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(facade.findAll(productId, pageable));
    }

    @Override
    public ResponseEntity<List<PriceDto>> findAllByProduct(
            ProductDto productDto,
            Pageable pageable
    ) {
        return ResponseEntity.ok(facade.findAll(productDto.id(), pageable));
    }

    @Override
    public ResponseEntity<List<PriceDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(facade.findAll(pageable));
    }

    @Override
    public ResponseEntity<PriceDto> update(PriceDto dto) {
        return ResponseEntity.accepted().body(facade.update(dto));
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        facade.delete(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<PriceDto> find(Long id) {
        return ResponseEntity.ok(facade.find(id));
    }
}

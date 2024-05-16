package dev.vlaship.backoffice.controller;

import dev.vlaship.backoffice.api.PriceApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import dev.vlaship.backoffice.dto.BetweenPrice;
import dev.vlaship.backoffice.dto.PriceDto;
import dev.vlaship.backoffice.dto.ProductDto;
import dev.vlaship.backoffice.facade.PriceFacade;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PriceController implements PriceApi {

    private final PriceFacade facade;

    @Override
    public ResponseEntity<Void> deletePrice(Long id) {
        facade.delete(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<PriceDto> getPriceById(Long id) {
        return ResponseEntity.ok(facade.find(id));
    }

    @Override
    public ResponseEntity<List<PriceDto>> getPrices(Pageable pageable) {
        return ResponseEntity.ok(facade.findAll(pageable));
    }

    @Override
    public ResponseEntity<List<PriceDto>> getPricesBetween(Pageable pageable, BetweenPrice betweenPrice) {
        return ResponseEntity.ok(facade.findAll(betweenPrice, pageable));
    }

    @Override
    public ResponseEntity<List<PriceDto>> getPricesBetweenAndCurrency(String currency, BigDecimal from, BigDecimal to, Pageable pageable) {
        return ResponseEntity.ok(facade.findAll(currency, from, to, pageable));
    }

    @Override
    public ResponseEntity<List<PriceDto>> getPricesByCurrency(String currency, Pageable pageable) {
        return ResponseEntity.ok(facade.findAll(currency, pageable));
    }

    @Override
    public ResponseEntity<List<PriceDto>> getPricesByProduct(Pageable pageable, ProductDto productDto) {
        return ResponseEntity.ok(facade.findAll(productDto.getId(), pageable));
    }

    @Override
    public ResponseEntity<List<PriceDto>> getPricesByProductId(Long productId, Pageable pageable) {
        return ResponseEntity.ok(facade.findAll(productId, pageable));
    }

    @Override
    public ResponseEntity<PriceDto> updatePrice(PriceDto priceDto) {
        return ResponseEntity.accepted().body(facade.update(priceDto));
    }
}

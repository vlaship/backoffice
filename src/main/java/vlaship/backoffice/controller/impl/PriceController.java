package vlaship.backoffice.controller.impl;

import org.springframework.http.ResponseEntity;
import vlaship.backoffice.controller.AbstractController;
import vlaship.backoffice.dto.BetweenPrice;
import vlaship.backoffice.dto.PriceDto;
import vlaship.backoffice.dto.ProductDto;
import vlaship.backoffice.facade.impl.PriceFacade;
import vlaship.backoffice.model.Price;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.util.List;

@RestController
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

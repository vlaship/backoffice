package vlaship.backoffice.controller.impl;

import vlaship.backoffice.controller.AbstractController;
import vlaship.backoffice.dto.BetweenPrice;
import vlaship.backoffice.dto.PriceDto;
import vlaship.backoffice.dto.ProductDto;
import vlaship.backoffice.facade.impl.PriceFacade;
import vlaship.backoffice.model.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/price")
public class PriceController extends AbstractController<Price, PriceDto> {

    private final PriceFacade priceFacade;


    @GetMapping("/between/{currency}/{from}/{to}")
    public List<PriceDto> findAllBetween(final @PathVariable("currency") String currency,
                                         final @PathVariable("from") BigDecimal from,
                                         final @PathVariable("to") BigDecimal to, final Pageable pageable) {
        return priceFacade.findAll(pageable, currency, from, to);
    }

    @GetMapping("/between")
    public List<PriceDto> findAllBetween(@Valid final @RequestBody BetweenPrice betweenPrice,
                                         final Pageable pageable) {
        return priceFacade.findAll(pageable, betweenPrice);
    }

    @GetMapping("/currency/{currency}")
    public List<PriceDto> findAllByCurrency(final @PathVariable("currency") String currency, final Pageable pageable) {
        return priceFacade.findAll(pageable, currency);
    }

    @GetMapping("/product/{productId}")
    public List<PriceDto> findAllByProduct(final @PathVariable("productId") Integer productId,
                                           final Pageable pageable) {
        return priceFacade.findAll(pageable, productId);
    }

    @GetMapping("/product")
    public List<PriceDto> findAllByProduct(@Valid final @RequestBody ProductDto productDto,
                                           final Pageable pageable) {
        return priceFacade.findAll(pageable, productDto.getId());
    }

    @Autowired
    public PriceController(final PriceFacade priceFacade) {
        super(priceFacade);
        this.priceFacade = priceFacade;
    }

}

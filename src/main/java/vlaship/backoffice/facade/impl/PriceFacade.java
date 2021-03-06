package vlaship.backoffice.facade.impl;

import vlaship.backoffice.dto.BetweenPrice;
import vlaship.backoffice.dto.PriceDto;
import vlaship.backoffice.exception.DeleteException;
import vlaship.backoffice.model.Price;
import vlaship.backoffice.facade.AbstractFacade;
import vlaship.backoffice.facade.converter.impl.PriceConverter;
import vlaship.backoffice.service.impl.PriceService;
import vlaship.backoffice.service.impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PriceFacade extends AbstractFacade<Price, PriceDto> {

    private final PriceService priceService;
    private final PriceConverter priceConverter;
    private final ProductService productService;

    public List<PriceDto> findAll(final Pageable pageable,
                                  final String currency, final BigDecimal from,
                                  final BigDecimal to) {
        return priceService.findAll(pageable, currency, from, to).stream()
                .map(priceConverter::convert)
                .collect(Collectors.toList());
    }

    public List<PriceDto> findAll(final Pageable pageable, final BetweenPrice betweenPrice) {
        return findAll(pageable, betweenPrice.getCurrency(), betweenPrice.getFrom(), betweenPrice.getTo());
    }

    public List<PriceDto> findAll(final Pageable pageable, final Integer productId) {
        return priceService.findAll(pageable, productService.get(productId)).stream()
                .map(priceConverter::convert)
                .collect(Collectors.toList());
    }

    public List<PriceDto> findAll(final Pageable pageable, final String currency) {
        return priceService.findAll(pageable, currency).stream()
                .map(priceConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    protected void checkForDelete(final Price price) {
        if (priceService.countAllByProduct(price.getProduct()) < 2) {
            throw new DeleteException("last " + price + " in " + price.getProduct());
        }
    }

    @Autowired
    public PriceFacade(final PriceService priceService,
                       final PriceConverter priceConverter,
                       final ProductService productService) {
        super(priceConverter, priceService);
        this.priceService = priceService;
        this.priceConverter = priceConverter;
        this.productService = productService;
    }
}

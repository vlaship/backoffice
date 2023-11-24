package vlaship.backoffice.facade.impl;

import org.springframework.lang.NonNull;
import vlaship.backoffice.dto.BetweenPrice;
import vlaship.backoffice.dto.PriceDto;
import vlaship.backoffice.exception.DeleteException;
import vlaship.backoffice.model.Price;
import vlaship.backoffice.facade.AbstractFacade;
import vlaship.backoffice.mapper.impl.PriceMapper;
import vlaship.backoffice.service.impl.PriceService;
import vlaship.backoffice.service.impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class PriceFacade extends AbstractFacade<Price, PriceDto> {

    private final PriceService priceService;
    private final PriceMapper priceConverter;
    private final ProductService productService;

    public List<PriceDto> findAll(
            @NonNull final String currency,
            @NonNull final BigDecimal from,
            @NonNull final BigDecimal to,
            @NonNull final Pageable pageable
    ) {
        return priceService.findAll(currency, from, to, pageable)
                .stream()
                .map(priceConverter::map)
                .toList();
    }

    public List<PriceDto> findAll(
            @NonNull final BetweenPrice betweenPrice,
            @NonNull final Pageable pageable
    ) {
        return findAll(betweenPrice.currency(), betweenPrice.from(), betweenPrice.to(), pageable);
    }

    public List<PriceDto> findAll(
            @NonNull final Long productId,
            @NonNull final Pageable pageable
    ) {
        return priceService.findAll(productService.get(productId), pageable)
                .stream()
                .map(priceConverter::map)
                .toList();
    }

    public List<PriceDto> findAll(
            @NonNull final String currency,
            @NonNull final Pageable pageable
    ) {
        return priceService.findAll(currency, pageable)
                .stream()
                .map(priceConverter::map)
                .toList();
    }

    @Override
    protected void checkForDelete(@NonNull final Price price) {
        if (priceService.countAllByProduct(price.getProduct()) < 2) {
            throw new DeleteException("last " + price + " in " + price.getProduct());
        }
    }

    @Autowired
    public PriceFacade(
            final PriceService priceService,
            final PriceMapper priceConverter,
            final ProductService productService
    ) {
        super(priceConverter, priceService);
        this.priceService = priceService;
        this.priceConverter = priceConverter;
        this.productService = productService;
    }

}

package dev.vlaship.backoffice.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import dev.vlaship.backoffice.dto.BetweenPrice;
import dev.vlaship.backoffice.dto.PriceDto;
import dev.vlaship.backoffice.exception.DeleteException;
import dev.vlaship.backoffice.model.Price;
import dev.vlaship.backoffice.mapper.PriceMapper;
import dev.vlaship.backoffice.service.impl.PriceService;
import dev.vlaship.backoffice.service.impl.ProductService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PriceFacade {

    private final PriceService service;
    private final PriceMapper mapper;
    private final ProductService productService;

    public List<PriceDto> findAll(
            @NonNull final String currency,
            @NonNull final BigDecimal from,
            @NonNull final BigDecimal to,
            @NonNull final Pageable pageable
    ) {
        return service.findAll(currency, from, to, pageable)
                .stream()
                .map(mapper::map)
                .toList();
    }

    public List<PriceDto> findAll(
            @NonNull final BetweenPrice betweenPrice,
            @NonNull final Pageable pageable
    ) {
        return findAll(betweenPrice.getCurrency(), betweenPrice.getFrom(), betweenPrice.getTo(), pageable);
    }

    public List<PriceDto> findAll(
            @NonNull final Long productId,
            @NonNull final Pageable pageable
    ) {
        return service.findAll(productService.get(productId), pageable)
                .stream()
                .map(mapper::map)
                .toList();
    }

    public List<PriceDto> findAll(
            @NonNull final String currency,
            @NonNull final Pageable pageable
    ) {
        return service.findAll(currency, pageable)
                .stream()
                .map(mapper::map)
                .toList();
    }

    protected void checkForDelete(@NonNull final Price price) {
        if (service.countAllByProduct(price.getProduct()) < 2) {
            throw new DeleteException("last " + price + " in " + price.getProduct());
        }
    }

    @NonNull
    public Price get(@NonNull final Long id) {
        return service.find(id);
    }

    @NonNull
    private Price get(@NonNull final PriceDto d) {
        return get(d.getId());
    }

    @NonNull
    public PriceDto update(@NonNull final PriceDto dto) {
        Price m = mapper.merge(dto, get(dto));
        final Price saved = service.save(m);
        return mapper.map(saved);
    }

    @NonNull
    public void delete(@NonNull final Long id) {
        final Price m = get(id);
        checkForDelete(m);
        service.delete(m);
    }

    @NonNull
    public PriceDto find(@NonNull final Long id) {
        return mapper.map(service.find(id));
    }

    @NonNull
    public List<PriceDto> findAll(@NonNull final Pageable pageable) {
        return service.findAll(pageable).stream().map(mapper::map).toList();
    }

}

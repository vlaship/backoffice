package dev.vlaship.backoffice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import dev.vlaship.backoffice.model.Price;
import dev.vlaship.backoffice.model.Product;
import dev.vlaship.backoffice.repository.PriceRepository;
import dev.vlaship.backoffice.service.AbstractService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

@Slf4j
@Service
@Transactional
public class PriceService extends AbstractService<Price> {

    private final PriceRepository repository;

    @NonNull
    public List<Price> findAll(
            @NonNull String currency,
            @NonNull Pageable pageable
    ) {
        return repository.findAllByCurrency(Currency.getInstance(currency.toUpperCase()), pageable);
    }

    @NonNull
    public List<Price> findAll(
            @NonNull Product product,
            @NonNull Pageable pageable
    ) {
        return repository.findAllByProduct(product, pageable);
    }

    @NonNull
    public List<Price> findAll(
            @NonNull String currency,
            @NonNull BigDecimal from,
            @NonNull BigDecimal to,
            @NonNull Pageable pageable
    ) {
        return repository.findAllByAmountBetweenAndCurrency(
                from,
                to,
                Currency.getInstance(currency.toUpperCase()),
                pageable
        );
    }

    public int countAllByProduct(@NonNull Product product) {
        return repository.countAllByProduct(product);
    }

    public PriceService(final PriceRepository repository) {
        super(repository);
        this.repository = repository;
        setTypeClass(Price.class);
    }

}

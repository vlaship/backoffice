package vlaship.backoffice.service.impl;

import org.springframework.lang.NonNull;
import vlaship.backoffice.model.Price;
import vlaship.backoffice.model.Product;
import vlaship.backoffice.repository.PriceRepository;
import vlaship.backoffice.service.AbstractService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

@Service
@Transactional
public class PriceService extends AbstractService<Price> {

    private final PriceRepository repository;

    @NonNull
    public List<Price> findAll(@NonNull final Pageable pageable, @NonNull final String currency) {
        return repository.findAllByCurrency(Currency.getInstance(currency.toUpperCase()), pageable);
    }

    @NonNull
    public List<Price> findAll(@NonNull final Pageable pageable, @NonNull final Product product) {
        return repository.findAllByProduct(product, pageable);
    }

    @NonNull
    public List<Price> findAll(
            @NonNull final Pageable pageable,
            @NonNull final String currency,
            @NonNull final BigDecimal from,
            @NonNull final BigDecimal to
    ) {
        return repository.findAllByAmountBetweenAndCurrency(
                from,
                to,
                Currency.getInstance(currency.toUpperCase()),
                pageable
        );
    }

    public int countAllByProduct(@NonNull final Product product) {
        return repository.countAllByProduct(product);
    }

    public PriceService(final PriceRepository repository) {
        super(repository);
        this.repository = repository;
        setTypeClass(Price.class);
    }

}

package vlaship.backoffice.service.impl;

import vlaship.backoffice.model.Price;
import vlaship.backoffice.model.Product;
import vlaship.backoffice.repository.PriceRepository;
import vlaship.backoffice.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Price> findAll(final Pageable pageable, final String currency) {
        return repository.findAllByCurrency(Currency.getInstance(currency.toUpperCase()), pageable);
    }

    public List<Price> findAll(final Pageable pageable, final Product product) {
        return repository.findAllByProduct(product, pageable);
    }

    public List<Price> findAll(final Pageable pageable,
                               final String currency, final BigDecimal from,
                               final BigDecimal to) {
        return repository.findAllByAmountBetweenAndCurrency(from, to,
                Currency.getInstance(currency.toUpperCase()), pageable);
    }

    public int countAllByProduct(final Product product) {
        return repository.countAllByProduct(product);
    }


    @Autowired
    public PriceService(final PriceRepository repository) {
        super(repository);
        this.repository = repository;
        setTypeClass(Price.class);
    }
}

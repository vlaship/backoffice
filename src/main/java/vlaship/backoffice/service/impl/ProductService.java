package vlaship.backoffice.service.impl;

import org.springframework.lang.NonNull;
import vlaship.backoffice.model.Category;
import vlaship.backoffice.model.Product;
import vlaship.backoffice.repository.ProductRepository;
import vlaship.backoffice.service.AbstractService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

@Service
@Transactional
public class ProductService extends AbstractService<Product> {

    private final ProductRepository repository;

    @NonNull
    public List<Product> findAll(
            @NonNull final String name,
            @NonNull final Pageable pageable
    ) {
        return repository.findAllByName(name, pageable);
    }

    @NonNull
    public List<Product> findAll(
            @NonNull final List<Category> categories,
            @NonNull final Pageable pageable
    ) {
        var ids = categories.stream()
                .map(Category::getId)
                .toList();
        return repository.findAllByCategories(ids, pageable);
    }

    @NonNull
    public List<Product> findAll(
            @NonNull final BigDecimal amount,
            @NonNull final String currency,
            @NonNull final Pageable pageable
    ) {
        return repository.findAllByPrice(amount, Currency.getInstance(currency.toUpperCase()), pageable);
    }

    public ProductService(final ProductRepository repository) {
        super(repository);
        this.repository = repository;
        setTypeClass(Product.class);
    }

}

package dev.vlaship.backoffice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import dev.vlaship.backoffice.model.Category;
import dev.vlaship.backoffice.model.Product;
import dev.vlaship.backoffice.repository.ProductRepository;
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
public class ProductService extends AbstractService<Product> {

    private final ProductRepository repository;

    @NonNull
    public List<Product> findAll(
            @NonNull String name,
            @NonNull Pageable pageable
    ) {
        return repository.findAllByName(name, pageable);
    }

    @NonNull
    public List<Product> findAll(
            @NonNull List<Category> categories,
            @NonNull Pageable pageable
    ) {
        var ids = categories.stream()
                .map(Category::getId)
                .toList();
        return repository.findAllByCategories(ids, pageable);
    }

    @NonNull
    public List<Product> findAll(
            @NonNull BigDecimal amount,
            @NonNull String currency,
            @NonNull Pageable pageable
    ) {
        return repository.findAllByPrice(amount, Currency.getInstance(currency.toUpperCase()), pageable);
    }

    public ProductService(final ProductRepository repository) {
        super(repository);
        this.repository = repository;
        setTypeClass(Product.class);
    }

}

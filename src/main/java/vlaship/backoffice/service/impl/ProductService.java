package vlaship.backoffice.service.impl;

import vlaship.backoffice.model.Category;
import vlaship.backoffice.model.Product;
import vlaship.backoffice.repository.ProductRepository;
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
public class ProductService extends AbstractService<Product> {

    private final ProductRepository repository;

    public List<Product> findAll(final Pageable pageable, final String name) {
        return repository.findAllByName(name, pageable);
    }

    public List<Product> findAll(final Pageable pageable, final Category category) {
        return repository.findAllByCategories(category, pageable);
    }

    public List<Product> findAll(final Pageable pageable, final BigDecimal amount, final String currency) {
        return repository.findAllByPrice(amount, Currency.getInstance(currency.toUpperCase()), pageable);
    }

    @Autowired
    public ProductService(final ProductRepository repository) {
        super(repository);
        this.repository = repository;
        setTypeClass(Product.class);
    }
}

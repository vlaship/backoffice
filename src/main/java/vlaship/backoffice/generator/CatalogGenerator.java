package vlaship.backoffice.generator;

import vlaship.backoffice.model.*;
import vlaship.backoffice.repository.CategoryRepository;
import vlaship.backoffice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Random;

@Transactional
@Component
public class CatalogGenerator {

    private static final int COUNT = 100;
    private static final Random RANDOM = new Random();

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CatalogGenerator(final ProductRepository productRepository, final CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public void catalog() {
        categories();
        products();
    }

    private void categories() {
        categoryRepository.save(new Category("root"));
        List<Category> categoryList = new ArrayList<>();
        for (int i = 1; i < COUNT; i++) {
            categoryList.add(new Category("category_" + i));
        }
        categoryRepository.saveAll(categoryList);
        categoryList.forEach(x -> x.setParent(categoryRepository.findById(rnd(COUNT)).orElseThrow()));
        categoryRepository.saveAll(categoryList);
    }

    private void products() {
        final List<Product> productList = new ArrayList<>();
        for (int i = 0; i < COUNT; i++) {

            final Price byn = new Price(BigDecimal.valueOf(rnd(1000)), Currency.getInstance("BYN"));
            final Price eur = new Price(BigDecimal.valueOf(rnd(1000)), Currency.getInstance("EUR"));
            final Price usd = new Price(BigDecimal.valueOf(rnd(1000)), Currency.getInstance("USD"));

            final Product product = new Product();
            product.setName("product_" + i);

            product.getPrices().add(byn);
            product.getPrices().add(eur);
            product.getPrices().add(usd);

            byn.setProduct(product);
            eur.setProduct(product);
            usd.setProduct(product);

            product.getCategories().add(categoryRepository.findById(rnd(COUNT)).orElseThrow());
            product.getCategories().add(categoryRepository.findById(rnd(COUNT)).orElseThrow());
            product.getCategories().add(categoryRepository.findById(rnd(COUNT)).orElseThrow());

            productList.add(product);
        }
        productRepository.saveAll(productList);
    }

    private int rnd(final int max) {
        return RANDOM.nextInt(max - 1) + 1;
    }
}

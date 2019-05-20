package vlaship.backoffice.repository;

import vlaship.backoffice.model.Category;
import vlaship.backoffice.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllByName(String name, Pageable pageable);

    @Query("SELECT prod FROM Price price INNER JOIN Product prod ON prod.id = price.product.id"
            + " WHERE price.amount = :amount AND price.currency LIKE :currency")
    List<Product> findAllByPrice(@Param("amount") BigDecimal amount, @Param("currency") Currency currency,
                                 Pageable pageable);

    List<Product> findAllByCategories(Category category, Pageable pageable);

}

package dev.vlaship.backoffice.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import dev.vlaship.backoffice.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(value = "Product.prices_categories", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Product> findById(Long id);

    List<Product> findAllByName(String name, Pageable pageable);

    @Query("""
            FROM Price price
            INNER JOIN Product prod ON prod.id = price.product.id
            WHERE price.amount = :amount AND price.currency = :currency
            """)
    List<Product> findAllByPrice(
            @Param("amount") BigDecimal amount,
            @Param("currency") Currency currency,
            Pageable pageable
    );

    @Query("""
            FROM Product prod
            INNER JOIN prod.categories cat ON cat.id IN :categoryIds
            """)
    List<Product> findAllByCategories(List<Long> categoryIds, Pageable pageable);

}

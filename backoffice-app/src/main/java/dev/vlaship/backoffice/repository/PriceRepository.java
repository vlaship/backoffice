package dev.vlaship.backoffice.repository;

import dev.vlaship.backoffice.model.Price;
import dev.vlaship.backoffice.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

public interface PriceRepository extends JpaRepository<Price, Long> {

    List<Price> findAllByAmountBetweenAndCurrency(BigDecimal from, BigDecimal to, Currency currency, Pageable pageable);

    List<Price> findAllByCurrency(Currency currency, Pageable pageable);

    List<Price> findAllByProduct(Product product, Pageable pageable);

    Integer countAllByProduct(Product product);

}

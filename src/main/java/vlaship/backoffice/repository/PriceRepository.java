package vlaship.backoffice.repository;

import vlaship.backoffice.model.Price;
import vlaship.backoffice.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

public interface PriceRepository extends JpaRepository<Price, Integer> {

	List<Price> findAllByAmountBetweenAndCurrency(BigDecimal from, BigDecimal to, Currency currency, Pageable pageable);

	List<Price> findAllByCurrency(Currency currency, Pageable pageable);

	List<Price> findAllByProduct(Product product, Pageable pageable);

	Integer countAllByProduct(Product product);

}

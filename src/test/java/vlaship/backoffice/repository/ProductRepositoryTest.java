package vlaship.backoffice.repository;

import vlaship.backoffice.model.Category;
import vlaship.backoffice.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@DataJpaTest
 class ProductRepositoryTest {

	private static final String PROD = "prod_";

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductRepository repository;

	private final static Currency BYN = Currency.getInstance("BYN");

	private final static Currency EUR = Currency.getInstance("EUR");

	@Test
	public void test_findAllByPrice() {

		final List<Product> byPrice1 = repository.findAllByPrice(BigDecimal.valueOf(10.01), BYN, PageRequest.of(0, 10));
		then(byPrice1.size()).isEqualTo(3);
		then(byPrice1.get(0).getPrices().stream().anyMatch(x -> BYN.equals(x.getCurrency()))).isTrue();
		then(byPrice1.get(1).getPrices().stream().anyMatch(x -> BYN.equals(x.getCurrency()))).isTrue();

		final List<Product> byPrice2 = repository.findAllByPrice(BigDecimal.valueOf(20.01), EUR, PageRequest.of(0, 10));
		then(byPrice2.size()).isEqualTo(3);
		then(byPrice2.get(0).getPrices().stream().anyMatch(x -> EUR.equals(x.getCurrency()))).isTrue();
	}

	@Test
	public void test_findAllByCategories() {
		final Category cat1 = categoryRepository.getOne(3);
		final List<Product> byCategory1 = repository.findAllByCategories(cat1, PageRequest.of(0, 5));

		then(byCategory1.size()).isEqualTo(2);
		then(byCategory1.get(0).getName()).isEqualTo(PROD + "1");
		then(byCategory1.get(1).getName()).isEqualTo(PROD + "2");
		then(byCategory1.stream().allMatch(x -> x.getCategories().get(0) == cat1)).isTrue();

		final Category cat2 = categoryRepository.getOne(2);
		final List<Product> byCategory2 = repository.findAllByCategories(cat2, PageRequest.of(0, 5));

		then(byCategory2.size()).isEqualTo(1);
		then(byCategory2.get(0).getName()).isEqualTo(PROD + "3");
		then(byCategory2.stream().allMatch(x -> x.getCategories().get(0) == cat2)).isTrue();
	}

}

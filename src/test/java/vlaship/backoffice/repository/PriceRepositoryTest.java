package vlaship.backoffice.repository;

import org.junit.jupiter.api.Test;
import vlaship.backoffice.model.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@DataJpaTest
 class PriceRepositoryTest {

	@Autowired
	private PriceRepository repository;

	@Autowired
	private ProductRepository productRepository;

	private final static Currency BYN = Currency.getInstance("BYN");

	private final static Currency EUR = Currency.getInstance("EUR");

	private final static Currency USD = Currency.getInstance("USD");

	@Test
	public void test_findAllByAmountBetweenAndCurrency() {

		final List<Price> byn = repository.findAllByAmountBetweenAndCurrency(BigDecimal.valueOf(10),
				BigDecimal.valueOf(100), BYN, PageRequest.of(0, 10));

		then(byn.size()).isEqualTo(3);
		then(byn.get(0).getAmount()).isEqualTo(BigDecimal.valueOf(10.01));
		then(byn.stream().allMatch(x -> BYN.equals(x.getCurrency()))).isTrue();

		final List<Price> eur = repository.findAllByAmountBetweenAndCurrency(BigDecimal.valueOf(10),
				BigDecimal.valueOf(100), EUR, PageRequest.of(1, 2));
		then(eur.size()).isEqualTo(1);
		then(eur.get(0).getAmount()).isEqualTo(BigDecimal.valueOf(20.01));
		then(eur.get(0).getCurrency()).isEqualTo(EUR);

		final List<Price> usd = repository.findAllByAmountBetweenAndCurrency(BigDecimal.valueOf(10),
				BigDecimal.valueOf(100), USD, PageRequest.of(0, 10));
		then(usd.size()).isEqualTo(3);
		then(usd.get(0).getAmount()).isEqualTo(BigDecimal.valueOf(30.01));
	}

	@Test
	public void test_findAllByCurrency() {
		final List<Price> list = repository.findAllByCurrency(BYN, PageRequest.of(0, 10));
		then(list.size()).isEqualTo(3);
		then(list.stream().allMatch(x -> BYN.equals(x.getCurrency()))).isTrue();
	}

	@Test
	public void test_findAllByProduct() {
		final List<Price> list1 = repository.findAllByProduct(productRepository.getOne(1), PageRequest.of(0, 5));
		then(list1.size()).isEqualTo(3);
		then(list1.stream().allMatch(x -> x.getProduct().getId() == 1)).isTrue();

		final List<Price> list3 = repository.findAllByProduct(productRepository.getOne(3), PageRequest.of(0, 5));
		then(list3.size()).isEqualTo(3);
		then(list3.stream().allMatch(x -> x.getProduct().getId() == 3)).isTrue();
	}

	@Test
	public void test_countAllByProduct() {
		final Integer count = repository.countAllByProduct(productRepository.getOne(1));
		then(count).isEqualTo(3);
	}

}

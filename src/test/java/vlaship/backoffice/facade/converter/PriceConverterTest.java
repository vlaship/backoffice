package vlaship.backoffice.facade.converter;

import org.junit.jupiter.api.BeforeAll;
import vlaship.backoffice.dto.PriceDto;
import vlaship.backoffice.mapper.impl.PriceMapper;
import vlaship.backoffice.model.Price;
import vlaship.backoffice.model.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

public class PriceConverterTest {

	private PriceMapper testSubject = new PriceMapper();

	private static List<Price> models;

	private static List<PriceDto> dtos;

	@BeforeAll
	public static void setupDtos() {

		dtos = new ArrayList<>(
				Arrays.asList(PriceDto.builder().id(1l).currency("byn").amount(BigDecimal.valueOf(100)).build(),
						PriceDto.builder().id(2l).currency("eur").amount(BigDecimal.valueOf(200)).build(),
						PriceDto.builder().id(3l).currency("usd").amount(BigDecimal.valueOf(300)).build()));
	}

	@BeforeAll
	public static void setupModels() {

		models = new ArrayList<>();

		final Price price1 = new Price(BigDecimal.valueOf(101), Currency.getInstance("PLN"));
		final Price price2 = new Price(BigDecimal.valueOf(202), Currency.getInstance("UAH"));
		final Price price3 = new Price(BigDecimal.valueOf(303), Currency.getInstance("RUR"));

		models.add(price1);
		models.add(price2);
		models.add(price3);

		final Product product1 = new Product();
		final Product product2 = new Product();
		product1.setId(1l);
		product2.setId(2l);

		price1.setProduct(product1);
		price2.setProduct(product1);
		price3.setProduct(product2);
	}

	@Test
	public void test_convert_PriceToPriceDto() {
		for (int i = 0; i < 3; i++) {
			final PriceDto dto = testSubject.map(models.get(i));
			then(dto.id()).isEqualTo(models.get(i).getId());
			then(dto.amount()).isEqualTo(models.get(i).getAmount());
			then(dto.currency()).isEqualTo(models.get(i).getCurrency().getCurrencyCode());
			then(dto.productId()).isEqualTo(models.get(i).getProduct().getId());
		}
	}

	@Test
	public void test_convert_PriceDtoToPrice() {
		for (int i = 0; i < 3; i++) {
			final Price model = testSubject.map(dtos.get(i));
			then(model.getAmount()).isEqualTo(dtos.get(i).amount());
			then(model.getCurrency().getCurrencyCode()).isEqualToIgnoringCase(dtos.get(i).currency());
			then(model.getId()).isNull();
			then(model.getProduct()).isNull();
		}
	}

	@Test
	public void test_convert_PriceDtoPriceToPrice() {
		for (int i = 0; i < 3; i++) {
			final Price model = testSubject.merge(dtos.get(i), models.get(i));
			then(model.getId()).isEqualTo(models.get(i).getId());
			then(model.getAmount()).isEqualTo(dtos.get(i).amount());
			then(model.getCurrency().getCurrencyCode()).isEqualToIgnoringCase(dtos.get(i).currency());
			then(model.getProduct()).isEqualTo(models.get(i).getProduct());
		}
	}

}

package vlaship.backoffice.facade.converter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import vlaship.backoffice.dto.ProductCreationDto;
import vlaship.backoffice.dto.ProductDto;
import vlaship.backoffice.facade.converter.impl.ProductConverter;
import vlaship.backoffice.model.Category;
import vlaship.backoffice.model.Price;
import vlaship.backoffice.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

public class ProductConverterTest {

	private ProductConverter testSubject = new ProductConverter();

	private static List<Product> models;

	private static List<Category> categories;

	private static List<ProductDto> dtos;

	private static List<ProductCreationDto> creationDtos;

	@BeforeAll
	public static void setupDtos() {

		dtos = new ArrayList<>(Arrays.asList(ProductDto.builder().id(1).name("prod1").build(),
				ProductDto.builder().id(2).name("prod2").build()));
	}

	@BeforeAll
	public static void setupCreationDtos() {

		creationDtos = new ArrayList<>(Arrays.asList(
				ProductCreationDto.builder().name("prod1").amount(BigDecimal.valueOf(1)).currency("byn").categoryId(1)
						.build(),
				ProductCreationDto.builder().name("prod2").amount(BigDecimal.valueOf(2)).currency("eur").categoryId(2)
						.build()));
	}

	@BeforeAll
	public static void setupModels() {

		final Category cat1 = new Category("cat1");
		final Category cat2 = new Category("cat2");
		cat1.setId(3);
		cat2.setId(4);

		categories = new ArrayList<>();
		categories.add(cat1);
		categories.add(cat2);

		final Price price1 = new Price(BigDecimal.valueOf(101), Currency.getInstance("PLN"));
		final Price price2 = new Price(BigDecimal.valueOf(202), Currency.getInstance("UAH"));
		price1.setId(1);
		price2.setId(2);

		models = new ArrayList<>();

		final Product product1 = new Product();
		final Product product2 = new Product();
		product1.setId(1);
		product1.setName("product1");
		product2.setId(2);
		product2.setName("product2");

		product1.getCategories().add(cat1);
		product2.getCategories().add(cat2);

		product1.getPrices().add(price1);
		product2.getPrices().add(price2);

		models.add(product1);
		models.add(product2);
	}

	@Test
	public void test_convert_ProductDtoProductToProduct() {
		for (int i = 0; i < 2; i++) {
			final Product product = testSubject.convert(dtos.get(i), models.get(i));

			then(product.getId()).isEqualTo(models.get(i).getId());
			then(product.getName()).isEqualTo(dtos.get(i).getName());
			then(product.getCategories().size()).isEqualTo(models.get(i).getCategories().size());
			then(product.getCategories().get(0).getId()).isEqualTo(models.get(i).getCategories().get(0).getId());
			then(product.getPrices().size()).isEqualTo(models.get(i).getPrices().size());
			then(product.getPrices().get(0).getId()).isEqualTo(models.get(i).getPrices().get(0).getId());
		}
	}

	@Test
	public void test_convert_ProductToProductDto() {
		for (Product product : models) {
			final ProductDto dto = testSubject.convert(product);
			then(dto.getId()).isEqualTo(product.getId());
			then(dto.getName()).isEqualTo(product.getName());
			then(dto.getCategories().size()).isEqualTo(product.getCategories().size());
			then(dto.getCategories().get(0)).isEqualTo(product.getCategories().get(0).getId());
			then(dto.getPrices().size()).isEqualTo(product.getPrices().size());
			then(dto.getPrices().get(0)).isEqualTo(product.getPrices().get(0).getId());
		}
	}

	@Test
	public void test_convert_ProductCreationDtoToProduct() {
		for (int i = 0; i < 2; i++) {
			final Product product = testSubject.convert(creationDtos.get(i), categories.get(i));

			then(product.getName()).isEqualTo(creationDtos.get(i).getName());

			then(product.getCategories().get(0).getId()).isEqualTo(categories.get(i).getId());
			then(product.getCategories().get(0).getName()).isEqualTo(categories.get(i).getName());

			then(product.getPrices().get(0).getAmount()).isEqualTo(creationDtos.get(i).getAmount());
			then(product.getPrices().get(0).getCurrency().getCurrencyCode())
					.isEqualToIgnoringCase(creationDtos.get(i).getCurrency());
		}
	}

//	@Test(expected = IllegalStateException.class)
	public void test_NPE() {
		testSubject.convert(null);
		testSubject.convert(null, new Category());
		testSubject.convert(new ProductDto(), null);
	}

}

package vlaship.backoffice.facade.converter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import vlaship.backoffice.dto.ProductCreationDto;
import vlaship.backoffice.dto.ProductDto;
import vlaship.backoffice.mapper.impl.ProductMapper;
import vlaship.backoffice.model.Category;
import vlaship.backoffice.model.Price;
import vlaship.backoffice.model.Product;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

public class ProductConverterTest {

    private ProductMapper testSubject = new ProductMapper();

    private static List<Product> models;

    private static List<Category> categories;

    private static List<ProductDto> dtos;

    private static List<ProductCreationDto> creationDtos;

    @BeforeAll
    static void setupDtos() {

        dtos = List.of(ProductDto.builder().id(1l).name("prod1").build(),
                ProductDto.builder().id(2l).name("prod2").build());
    }

    @BeforeAll
    static void setupCreationDtos() {

        creationDtos = List.of(
                ProductCreationDto.builder().name("prod1").amount(BigDecimal.valueOf(1)).currency("byn").categoryId(1l)
                        .build(),
                ProductCreationDto.builder().name("prod2").amount(BigDecimal.valueOf(2)).currency("eur").categoryId(2l)
                        .build());
    }

    @BeforeAll
    static void setupModels() {

        final Category cat1 = new Category("cat1");
        final Category cat2 = new Category("cat2");
        cat1.setId(3l);
        cat2.setId(4l);

        categories = List.of(cat1, cat2);

        final Price price1 = new Price(BigDecimal.valueOf(101), Currency.getInstance("PLN"));
        final Price price2 = new Price(BigDecimal.valueOf(202), Currency.getInstance("UAH"));
        price1.setId(1l);
        price2.setId(2l);

        final Product product1 = new Product();
        final Product product2 = new Product();
        product1.setId(1l);
        product1.setName("product1");
        product2.setId(2l);
        product2.setName("product2");

        product1.getCategories().add(cat1);
        product2.getCategories().add(cat2);

        product1.getPrices().add(price1);
        product2.getPrices().add(price2);

        models = List.of(product1, product2);
    }

    @Test
    void test_convert_ProductDtoProductToProduct() {
        for (int i = 0; i < 2; i++) {
            final Product product = testSubject.merge(dtos.get(i), models.get(i));

            then(product.getId()).isEqualTo(models.get(i).getId());
            then(product.getName()).isEqualTo(dtos.get(i).name());
            then(product.getCategories().size()).isEqualTo(models.get(i).getCategories().size());
            then(product.getCategories().get(0).getId()).isEqualTo(models.get(i).getCategories().get(0).getId());
            then(product.getPrices().size()).isEqualTo(models.get(i).getPrices().size());
            then(product.getPrices().get(0).getId()).isEqualTo(models.get(i).getPrices().get(0).getId());
        }
    }

    @Test
    void test_convert_ProductToProductDto() {
        for (Product product : models) {
            final ProductDto dto = testSubject.map(product);
            then(dto.id()).isEqualTo(product.getId());
            then(dto.name()).isEqualTo(product.getName());
            then(dto.categories().size()).isEqualTo(product.getCategories().size());
            then(dto.categories().get(0)).isEqualTo(product.getCategories().get(0).getId());
            then(dto.prices().size()).isEqualTo(product.getPrices().size());
            then(dto.prices().get(0)).isEqualTo(product.getPrices().get(0).getId());
        }
    }

    @Test
    void test_convert_ProductCreationDtoToProduct() {
        for (int i = 0; i < 2; i++) {
            final Product product = testSubject.map(creationDtos.get(i), categories.get(i));

            then(product.getName()).isEqualTo(creationDtos.get(i).name());

            then(product.getCategories().get(0).getId()).isEqualTo(categories.get(i).getId());
            then(product.getCategories().get(0).getName()).isEqualTo(categories.get(i).getName());

            then(product.getPrices().get(0).getAmount()).isEqualTo(creationDtos.get(i).amount());
            then(product.getPrices().get(0).getCurrency().getCurrencyCode())
                    .isEqualToIgnoringCase(creationDtos.get(i).currency());
        }
    }

    //	@Test(expected = IllegalStateException.class)
    void test_NPE() {
        testSubject.map(null);
        testSubject.map(null, new Category());
        testSubject.merge(ProductDto.builder().build(), null);
    }

}

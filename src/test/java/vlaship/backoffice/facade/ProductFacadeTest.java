package vlaship.backoffice.facade;

import org.junit.jupiter.api.Test;
import vlaship.backoffice.dto.PriceDto;
import vlaship.backoffice.dto.ProductDto;
import vlaship.backoffice.mapper.impl.PriceMapper;
import vlaship.backoffice.mapper.impl.ProductMapper;
import vlaship.backoffice.facade.impl.CategoryFacade;
import vlaship.backoffice.facade.impl.ProductFacade;
import vlaship.backoffice.model.Category;
import vlaship.backoffice.model.Price;
import vlaship.backoffice.model.Product;
import vlaship.backoffice.repository.PriceRepository;
import vlaship.backoffice.repository.ProductRepository;
import vlaship.backoffice.service.impl.PriceService;
import vlaship.backoffice.service.impl.ProductService;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.mock;

class ProductFacadeTest {

    private static final String FOUND = "found";

    private static final String NAME = "name";

    private final static String BYN = "BYN";

    private ProductRepository productRepository = mock(ProductRepository.class);

    private CategoryFacade categoryFacade = mock(CategoryFacade.class);

    private ProductFacade testSubject = new ProductFacade(new ProductService(productRepository), categoryFacade,
            new ProductMapper(), new PriceMapper(), new PriceService(mock(PriceRepository.class)));

    // @Test(expected = NotFoundException.class)
    // public void test_find() {
    // Mockito.when(productRepository.findByName(Mockito.anyString())).thenReturn(Optional.empty());
    // testSubject.find("");
    // }

    @Test
    void test_add_Price() {

        final Price price = new Price(BigDecimal.valueOf(100), Currency.getInstance(BYN));
        price.setId(2L);

        final Product found = new Product(FOUND);
        found.setId(1L);
        found.getPrices().add(new Price());

        final Product saved = new Product();
        saved.setId(1L);
        saved.getPrices().add(new Price());
        saved.getPrices().add(price);
        price.setProduct(saved);

        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(found));
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(saved);

        final ProductDto dto = testSubject.add(PriceDto.builder().currency(BYN).amount(BigDecimal.valueOf(100)).build(),
                1L);

        then(dto.prices().size()).isEqualTo(2);
    }

    @Test
    void test_add_Category() {

        final Category category = new Category(NAME);
        category.setId(2L);

        final Product found = new Product(FOUND);
        found.setId(1L);
        found.getCategories().add(new Category());

        final Product saved = new Product();
        saved.setId(1L);
        saved.getCategories().add(new Category());
        saved.getCategories().add(category);

        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(found));
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(saved);
        Mockito.when(categoryFacade.get(Mockito.anyLong())).thenReturn(category);

        final ProductDto dto = testSubject.add(2L, 1L);

        then(dto.categories().size()).isEqualTo(2);

    }

    //	@Test(expected = DeleteException.class)
    void test_remove() {
        final Product found = new Product(FOUND);
        found.setId(1L);
        found.getCategories().add(new Category());

        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(found));

        testSubject.removeCategory(2L, 1L);
    }

    // @Test(expected = SameNameException.class)
    // public void test_checkForSameName() {
    // Mockito.when(productRepository.findByName(Mockito.anyString())).thenReturn(Optional.of(new
    // Product()));
    // testSubject.update(ProductDto.builder().name(NAME).build());
    // }

}

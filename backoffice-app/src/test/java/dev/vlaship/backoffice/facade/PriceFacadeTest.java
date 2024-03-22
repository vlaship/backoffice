package dev.vlaship.backoffice.facade;

import dev.vlaship.backoffice.mapper.impl.PriceMapper;
import dev.vlaship.backoffice.model.Price;
import dev.vlaship.backoffice.model.Product;
import dev.vlaship.backoffice.repository.PriceRepository;
import dev.vlaship.backoffice.service.impl.PriceService;
import dev.vlaship.backoffice.service.impl.ProductService;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Mockito.mock;

class PriceFacadeTest {

    private PriceRepository priceRepository = mock(PriceRepository.class);

    private PriceFacade testSubject = new PriceFacade(new PriceService(priceRepository), new PriceMapper(),
            mock(ProductService.class));

    //	@Test(expected = DeleteException.class)
    void test_delete() {
        Mockito.when(priceRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(new Price()));
        Mockito.when(priceRepository.countAllByProduct(Mockito.any(Product.class))).thenReturn(1);
        testSubject.delete(0L);
    }

}

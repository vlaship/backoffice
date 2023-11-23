package vlaship.backoffice.facade;

import org.junit.jupiter.api.Test;
import vlaship.backoffice.exception.DeleteException;
import vlaship.backoffice.facade.converter.impl.PriceConverter;
import vlaship.backoffice.facade.impl.PriceFacade;
import vlaship.backoffice.model.Price;
import vlaship.backoffice.model.Product;
import vlaship.backoffice.repository.PriceRepository;
import vlaship.backoffice.service.impl.PriceService;
import vlaship.backoffice.service.impl.ProductService;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Mockito.mock;

public class PriceFacadeTest {

	private PriceRepository priceRepository = mock(PriceRepository.class);

	private PriceFacade testSubject = new PriceFacade(new PriceService(priceRepository), new PriceConverter(),
			mock(ProductService.class));

//	@Test(expected = DeleteException.class)
	public void test_delete() {
		Mockito.when(priceRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(new Price()));
		Mockito.when(priceRepository.countAllByProduct(Mockito.any(Product.class))).thenReturn(1);
		testSubject.delete(0);
	}

}

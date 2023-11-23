package vlaship.backoffice.facade.impl;

import org.springframework.lang.NonNull;
import vlaship.backoffice.dto.PriceDto;
import vlaship.backoffice.dto.ProductDto;
import vlaship.backoffice.dto.ProductCreationDto;
import vlaship.backoffice.exception.DeleteException;
import vlaship.backoffice.model.Category;
import vlaship.backoffice.model.Price;
import vlaship.backoffice.model.Product;
import vlaship.backoffice.facade.AbstractFacade;
import vlaship.backoffice.facade.converter.impl.PriceConverter;
import vlaship.backoffice.facade.converter.impl.ProductConverter;
import vlaship.backoffice.service.impl.PriceService;
import vlaship.backoffice.service.impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductFacade extends AbstractFacade<Product, ProductDto> {

	private final CategoryFacade categoryFacade;

	private final ProductConverter productConverter;

	private final ProductService productService;

	private final PriceConverter priceConverter;

	private final PriceService priceService;

	@NonNull
	public List<ProductDto> findAll(@NonNull final Pageable pageable, @NonNull final Integer categoryId) {
		return productService.findAll(pageable, categoryFacade.get(categoryId))
			.stream()
			.map(productConverter::convert)
			.collect(Collectors.toList());
	}

	@NonNull
	public List<ProductDto> findAll(@NonNull final Pageable pageable, @NonNull final String name) {
		return productService.findAll(pageable, name)
			.stream()
			.map(productConverter::convert)
			.collect(Collectors.toList());
	}

	@NonNull
	public List<ProductDto> findAll(@NonNull final Pageable pageable, @NonNull final PriceDto priceDto) {
		return productService.findAll(pageable, priceDto.getAmount(), priceDto.getCurrency())
			.stream()
			.map(productConverter::convert)
			.collect(Collectors.toList());
	}

	@NonNull
	public ProductDto create(@NonNull final ProductCreationDto productCreationDto) {
		final Category category = categoryFacade.get(productCreationDto.getCategoryId());
		final Product converted = productConverter.convert(productCreationDto, category);

		final Product saved = productService.save(converted);
		return productConverter.convert(saved);
	}

	@NonNull
	public ProductDto add(@NonNull final PriceDto priceDto, @NonNull final Integer productId) {
		final Product found = get(productId);
		final Price price = priceConverter.convert(priceDto);

		found.getPrices().add(price);
		price.setProduct(found);
		final Product saved = productService.save(found);
		return productConverter.convert(saved);
	}

	@NonNull
	public ProductDto add(@NonNull final Integer categoryId, @NonNull final Integer productId) {
		final Category category = categoryFacade.get(categoryId);
		final Product found = get(productId);

		found.getCategories().add(category);

		final Product saved = productService.save(found);
		return productConverter.convert(saved);
	}

	@NonNull
	public ProductDto removeCategory(@NonNull final Integer categoryId, @NonNull final Integer productId) {
		final Product found = get(productId);

		if (found.getCategories().size() < 2) {
			throw new DeleteException("last Category in Product");
		}

		final Category category = categoryFacade.get(categoryId);
		found.getCategories().remove(category);

		final Product saved = productService.save(found);
		return productConverter.convert(saved);
	}

	@NonNull
	public ProductDto removePrice(@NonNull final Integer priceId, @NonNull final Integer productId) {
		final Product found = get(productId);

		if (found.getPrices().size() < 2) {
			throw new DeleteException("last Price in Product");
		}

		final Price price = priceService.get(priceId);
		found.getPrices().remove(price);

		final Product saved = productService.save(found);
		return productConverter.convert(saved);
	}

	@Override
	protected void checkForDelete(@NonNull final Product product) {
	}

	public ProductFacade(final ProductService productService, final CategoryFacade categoryFacade,
			final ProductConverter productConverter, final PriceConverter priceConverter,
			final PriceService priceService) {
		super(productConverter, productService);
		this.productService = productService;
		this.categoryFacade = categoryFacade;
		this.productConverter = productConverter;
		this.priceConverter = priceConverter;
		this.priceService = priceService;
	}

}

package vlaship.backoffice.facade.converter.impl;

import org.springframework.lang.NonNull;
import vlaship.backoffice.dto.ProductDto;
import vlaship.backoffice.dto.ProductCreationDto;
import vlaship.backoffice.model.Category;
import vlaship.backoffice.model.Price;
import vlaship.backoffice.model.Product;
import vlaship.backoffice.facade.converter.BackOfficeConverter;
import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductConverter implements BackOfficeConverter<Product, ProductDto> {

	@NonNull
	@Override
	public ProductDto convert(@NonNull final Product product) {
		ProductDto dto = new ProductDto();

		dto.setId(product.getId());
		dto.setName(product.getName());

		final List<Integer> categoryList = product.getCategories()
			.stream()
			.map(Category::getId)
			.collect(Collectors.toList());
		dto.setCategories(categoryList);

		final List<Integer> priceList = product.getPrices().stream().map(Price::getId).collect(Collectors.toList());
		dto.setPrices(priceList);

		return dto;
	}

	@NonNull
	public Product convert(@NonNull final ProductCreationDto productCreationDto, @NonNull final Category category) {
		final Price price = new Price(productCreationDto.getAmount(),
				Currency.getInstance(productCreationDto.getCurrency().toUpperCase()));

		final Product product = new Product();
		product.setName(productCreationDto.getName());
		product.getPrices().add(price);
		price.setProduct(product);
		product.getCategories().add(category);

		return product;
	}

	@NonNull
	@Override
	public Product convert(@NonNull final ProductDto productDto, @NonNull final Product product) {
		product.setName(productDto.getName());
		return product;
	}

}

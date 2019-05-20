package vlaship.backoffice.facade.converter.impl;

import vlaship.backoffice.dto.ProductDto;
import vlaship.backoffice.dto.ProductCreationDto;
import vlaship.backoffice.model.Category;
import vlaship.backoffice.model.Price;
import vlaship.backoffice.model.Product;
import vlaship.backoffice.facade.converter.IConverter;
import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductConverter implements IConverter<Product, ProductDto> {

    @Override
    public ProductDto convert(final Product product) {
        isNull(product, Product.class.getSimpleName());

        ProductDto dto = new ProductDto();

        dto.setId(product.getId());
        dto.setName(product.getName());

        final List<Integer> categoryList = product.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toList());
        dto.setCategories(categoryList);

        final List<Integer> priceList = product.getPrices().stream()
                .map(Price::getId)
                .collect(Collectors.toList());
        dto.setPrices(priceList);

        return dto;
    }

    public Product convert(final ProductCreationDto productCreationDto, final Category category) {
        isNull(productCreationDto, ProductCreationDto.class.getSimpleName());
        isNull(category, Category.class.getSimpleName());

        final Price price = new Price(productCreationDto.getAmount(),
                Currency.getInstance(productCreationDto.getCurrency().toUpperCase()));

        final Product product = new Product();
        product.setName(productCreationDto.getName());
        product.getPrices().add(price);
        price.setProduct(product);
        product.getCategories().add(category);

        return product;
    }

    @Override
    public Product convert(final ProductDto productDto, final Product product) {
        isNull(product, Product.class.getSimpleName());
        isNull(productDto, ProductDto.class.getSimpleName());

        product.setName(productDto.getName());
        return product;
    }
}

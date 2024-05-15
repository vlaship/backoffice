package dev.vlaship.backoffice.mapper;

import org.springframework.lang.NonNull;
import dev.vlaship.backoffice.dto.ProductDto;
import dev.vlaship.backoffice.dto.ProductCreationDto;
import dev.vlaship.backoffice.model.Category;
import dev.vlaship.backoffice.model.Price;
import dev.vlaship.backoffice.model.Product;
import org.springframework.stereotype.Service;

import java.util.Currency;

@Service
public class ProductMapper {

    @NonNull
    public ProductDto map(@NonNull final Product product) {
        var categories = product.getCategories()
                .stream()
                .map(Category::getId)
                .toList();

        var prices = product.getPrices().stream()
                .map(Price::getId)
                .toList();

        return new ProductDto()
                .id(product.getId())
                .name(product.getName())
                .categories(categories)
                .prices(prices);
    }

    @NonNull
    public Product map(@NonNull final ProductCreationDto productCreationDto, @NonNull final Category category) {
        var price = Price.builder()
                .amount(productCreationDto.getAmount())
                .currency(Currency.getInstance(productCreationDto.getCurrency().toUpperCase()))
                .build();

        var product = new Product();
        product.setName(productCreationDto.getName());
        product.getPrices().add(price);
        price.setProduct(product);
        product.getCategories().add(category);

        return product;
    }

    @NonNull
    public Product merge(@NonNull final ProductDto productDto, @NonNull final Product product) {
        product.setName(productDto.getName());
        return product;
    }

}

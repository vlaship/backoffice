package dev.vlaship.backoffice.mapper.impl;

import org.springframework.lang.NonNull;
import dev.vlaship.backoffice.dto.ProductDto;
import dev.vlaship.backoffice.dto.ProductCreationDto;
import dev.vlaship.backoffice.model.Category;
import dev.vlaship.backoffice.model.Price;
import dev.vlaship.backoffice.model.Product;
import dev.vlaship.backoffice.mapper.BackOfficeMapper;
import org.springframework.stereotype.Service;

import java.util.Currency;

@Service
public class ProductMapper implements BackOfficeMapper<Product, ProductDto> {

    @NonNull
    @Override
    public ProductDto map(@NonNull final Product product) {
        var categories = product.getCategories()
                .stream()
                .map(Category::getId)
                .toList();

        var prices = product.getPrices().stream()
                .map(Price::getId)
                .toList();

        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .categories(categories)
                .prices(prices)
                .build();
    }

    @NonNull
    public Product map(@NonNull final ProductCreationDto productCreationDto, @NonNull final Category category) {
        var price = Price.builder()
                .amount(productCreationDto.amount())
                .currency(Currency.getInstance(productCreationDto.currency().toUpperCase()))
                .build();

        var product = new Product();
        product.setName(productCreationDto.name());
        product.getPrices().add(price);
        price.setProduct(product);
        product.getCategories().add(category);

        return product;
    }

    @NonNull
    @Override
    public Product merge(@NonNull final ProductDto productDto, @NonNull final Product product) {
        product.setName(productDto.name());
        return product;
    }

}

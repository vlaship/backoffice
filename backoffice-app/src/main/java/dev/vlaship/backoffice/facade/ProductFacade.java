package dev.vlaship.backoffice.facade;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import dev.vlaship.backoffice.dto.PriceDto;
import dev.vlaship.backoffice.dto.ProductDto;
import dev.vlaship.backoffice.dto.ProductCreationDto;
import dev.vlaship.backoffice.exception.DeleteException;
import dev.vlaship.backoffice.model.Category;
import dev.vlaship.backoffice.model.Price;
import dev.vlaship.backoffice.model.Product;
import dev.vlaship.backoffice.mapper.impl.PriceMapper;
import dev.vlaship.backoffice.mapper.impl.ProductMapper;
import dev.vlaship.backoffice.service.impl.PriceService;
import dev.vlaship.backoffice.service.impl.ProductService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class ProductFacade extends AbstractFacade<Product, ProductDto> {

    private final CategoryFacade categoryFacade;
    private final ProductMapper productConverter;
    private final ProductService productService;
    private final PriceMapper priceConverter;
    private final PriceService priceService;

    @NonNull
    public List<ProductDto> findAll(
            @NonNull final Long categoryId,
            @NonNull final Pageable pageable
    ) {
        return productService.findAll(List.of(categoryFacade.get(categoryId)), pageable).stream()
                .map(productConverter::map)
                .toList();
    }

    @NonNull
    public List<ProductDto> findAll(
            @NonNull final String name,
            @NonNull final Pageable pageable
    ) {
        return productService.findAll(name, pageable).stream()
                .map(productConverter::map)
                .toList();
    }

    @NonNull
    public List<ProductDto> findAll(
            @NonNull final PriceDto priceDto,
            @NonNull final Pageable pageable
    ) {
        return productService.findAll(priceDto.amount(), priceDto.currency(), pageable).stream()
                .map(productConverter::map)
                .toList();
    }

    @NonNull
    public ProductDto create(@NonNull final ProductCreationDto productCreationDto) {
        final Category category = categoryFacade.get(productCreationDto.categoryId());
        final Product converted = productConverter.map(productCreationDto, category);

        final Product saved = productService.save(converted);
        return productConverter.map(saved);
    }

    @NonNull
    public ProductDto add(
            @NonNull final PriceDto priceDto,
            @NonNull final Long productId
    ) {
        final Product found = get(productId);
        final Price price = priceConverter.map(priceDto);

        found.getPrices().add(price);
        price.setProduct(found);
        final Product saved = productService.save(found);
        return productConverter.map(saved);
    }

    @NonNull
    public ProductDto add(
            @NonNull final Long categoryId,
            @NonNull final Long productId
    ) {
        final Category category = categoryFacade.get(categoryId);
        final Product found = get(productId);

        found.getCategories().add(category);

        final Product saved = productService.save(found);
        return productConverter.map(saved);
    }

    @NonNull
    public ProductDto removeCategory(
            @NonNull final Long categoryId,
            @NonNull final Long productId
    ) {
        final Product found = get(productId);

        if (found.getCategories().size() < 2) {
            throw new DeleteException("last Category in Product");
        }

        final Category category = categoryFacade.get(categoryId);
        found.getCategories().remove(category);

        final Product saved = productService.save(found);
        return productConverter.map(saved);
    }

    @NonNull
    public ProductDto removePrice(
            @NonNull final Long priceId,
            @NonNull final Long productId
    ) {
        final Product found = get(productId);

        if (found.getPrices().size() < 2) {
            throw new DeleteException("last Price in Product");
        }

        final Price price = priceService.get(priceId);
        found.getPrices().remove(price);

        final Product saved = productService.save(found);
        return productConverter.map(saved);
    }

    @Override
    protected void checkForDelete(@NonNull final Product product) {
        throw new IllegalStateException();
    }

    public ProductFacade(
            final ProductService productService,
            final CategoryFacade categoryFacade,
            final ProductMapper productConverter,
            final PriceMapper priceConverter,
            final PriceService priceService
    ) {
        super(productConverter, productService);
        this.productService = productService;
        this.categoryFacade = categoryFacade;
        this.productConverter = productConverter;
        this.priceConverter = priceConverter;
        this.priceService = priceService;
    }

}

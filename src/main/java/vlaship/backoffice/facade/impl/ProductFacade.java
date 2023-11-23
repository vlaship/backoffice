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
import vlaship.backoffice.mapper.impl.PriceMapper;
import vlaship.backoffice.mapper.impl.ProductMapper;
import vlaship.backoffice.service.impl.PriceService;
import vlaship.backoffice.service.impl.ProductService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductFacade extends AbstractFacade<Product, ProductDto> {

    private final CategoryFacade categoryFacade;
    private final ProductMapper productConverter;
    private final ProductService productService;
    private final PriceMapper priceConverter;
    private final PriceService priceService;

    @NonNull
    public List<ProductDto> findAll(@NonNull final Pageable pageable, @NonNull final Long categoryId) {
        return productService.findAll(pageable, categoryFacade.get(categoryId))
                .stream()
                .map(productConverter::map)
                .collect(Collectors.toList());
    }

    @NonNull
    public List<ProductDto> findAll(@NonNull final Pageable pageable, @NonNull final String name) {
        return productService.findAll(pageable, name)
                .stream()
                .map(productConverter::map)
                .collect(Collectors.toList());
    }

    @NonNull
    public List<ProductDto> findAll(@NonNull final Pageable pageable, @NonNull final PriceDto priceDto) {
        return productService.findAll(pageable, priceDto.amount(), priceDto.currency())
                .stream()
                .map(productConverter::map)
                .collect(Collectors.toList());
    }

    @NonNull
    public ProductDto create(@NonNull final ProductCreationDto productCreationDto) {
        final Category category = categoryFacade.get(productCreationDto.categoryId());
        final Product converted = productConverter.map(productCreationDto, category);

        final Product saved = productService.save(converted);
        return productConverter.map(saved);
    }

    @NonNull
    public ProductDto add(@NonNull final PriceDto priceDto, @NonNull final Long productId) {
        final Product found = get(productId);
        final Price price = priceConverter.map(priceDto);

        found.getPrices().add(price);
        price.setProduct(found);
        final Product saved = productService.save(found);
        return productConverter.map(saved);
    }

    @NonNull
    public ProductDto add(@NonNull final Long categoryId, @NonNull final Long productId) {
        final Category category = categoryFacade.get(categoryId);
        final Product found = get(productId);

        found.getCategories().add(category);

        final Product saved = productService.save(found);
        return productConverter.map(saved);
    }

    @NonNull
    public ProductDto removeCategory(@NonNull final Long categoryId, @NonNull final Long productId) {
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
    public ProductDto removePrice(@NonNull final Long priceId, @NonNull final Long productId) {
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

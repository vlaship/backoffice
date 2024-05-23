package dev.vlaship.backoffice.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import dev.vlaship.backoffice.dto.PriceDto;
import dev.vlaship.backoffice.dto.ProductDto;
import dev.vlaship.backoffice.dto.ProductCreationDto;
import dev.vlaship.backoffice.exception.DeleteException;
import dev.vlaship.backoffice.model.Category;
import dev.vlaship.backoffice.model.Price;
import dev.vlaship.backoffice.model.Product;
import dev.vlaship.backoffice.mapper.PriceMapper;
import dev.vlaship.backoffice.mapper.ProductMapper;
import dev.vlaship.backoffice.service.impl.PriceService;
import dev.vlaship.backoffice.service.impl.ProductService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductFacade {

    private final CategoryFacade categoryFacade;
    private final ProductMapper mapper;
    private final ProductService service;
    private final PriceMapper priceMapper;
    private final PriceService priceService;

    @NonNull
    public List<ProductDto> findAll(
            @NonNull Long categoryId,
            @NonNull Pageable pageable
    ) {
        return service.findAll(List.of(categoryFacade.get(categoryId)), pageable).stream()
                .map(mapper::map)
                .toList();
    }

    @NonNull
    public List<ProductDto> findAll(
            @NonNull String name,
            @NonNull Pageable pageable
    ) {
        return service.findAll(name, pageable).stream()
                .map(mapper::map)
                .toList();
    }

    @NonNull
    public List<ProductDto> findAll(
            @NonNull PriceDto priceDto,
            @NonNull Pageable pageable
    ) {
        return service.findAll(priceDto.getAmount(), priceDto.getCurrency(), pageable).stream()
                .map(mapper::map)
                .toList();
    }

    @NonNull
    public ProductDto create(@NonNull ProductCreationDto productCreationDto) {
        final Category category = categoryFacade.get(productCreationDto.getCategoryId());
        final Product converted = mapper.map(productCreationDto, category);

        final Product saved = service.save(converted);
        return mapper.map(saved);
    }

    @NonNull
    public ProductDto add(
            @NonNull PriceDto priceDto,
            @NonNull Long productId
    ) {
        final Product found = get(productId);
        final Price price = priceMapper.map(priceDto);

        found.getPrices().add(price);
        price.setProduct(found);
        final Product saved = service.save(found);
        return mapper.map(saved);
    }

    @NonNull
    public ProductDto add(
            @NonNull Long categoryId,
            @NonNull Long productId
    ) {
        final Category category = categoryFacade.get(categoryId);
        final Product found = get(productId);

        found.getCategories().add(category);

        final Product saved = service.save(found);
        return mapper.map(saved);
    }

    @NonNull
    public ProductDto removeCategory(
            @NonNull Long categoryId,
            @NonNull Long productId
    ) {
        final Product found = get(productId);

        if (found.getCategories().size() < 2) {
            throw new DeleteException("last Category in Product");
        }

        final Category category = categoryFacade.get(categoryId);
        found.getCategories().remove(category);

        final Product saved = service.save(found);
        return mapper.map(saved);
    }

    @NonNull
    public ProductDto removePrice(
            @NonNull Long priceId,
            @NonNull Long productId
    ) {
        final Product found = get(productId);

        if (found.getPrices().size() < 2) {
            throw new DeleteException("last Price in Product");
        }

        final Price price = priceService.get(priceId);
        found.getPrices().remove(price);

        final Product saved = service.save(found);
        return mapper.map(saved);
    }

    @NonNull
    public Product get(@NonNull Long id) {
        return service.find(id);
    }

    @NonNull
    private Product get(@NonNull ProductDto d) {
        return get(d.getId());
    }

    @NonNull
    public ProductDto update(@NonNull ProductDto dto) {
        var m = mapper.merge(dto, get(dto));
        var saved = service.save(m);
        return mapper.map(saved);
    }

    @NonNull
    public void delete(@NonNull Long id) {
        var m = get(id);
        service.delete(m);
    }

    @NonNull
    public ProductDto find(@NonNull Long id) {
        return mapper.map(service.find(id));
    }

    @NonNull
    public List<ProductDto> findAll(@NonNull Pageable pageable) {
        return service.findAll(pageable).stream().map(mapper::map).toList();
    }
}

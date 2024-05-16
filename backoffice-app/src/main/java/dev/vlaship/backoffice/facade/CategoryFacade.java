package dev.vlaship.backoffice.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import dev.vlaship.backoffice.dto.CategoryDto;
import dev.vlaship.backoffice.exception.DeleteException;
import dev.vlaship.backoffice.model.Category;
import dev.vlaship.backoffice.mapper.CategoryMapper;
import dev.vlaship.backoffice.service.impl.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CategoryFacade {

    private final CategoryService service;
    private final CategoryMapper converter;

    @NonNull
    public CategoryDto create(@NonNull final CategoryDto categoryDto) {
        final Category converted = converter.map(categoryDto);
        final Category saved = service.save(converted);
        return converter.map(saved);
    }

    protected void checkForDelete(@NonNull final Category category) {
        if (!category.getSubCategories().isEmpty()) {
            throw new DeleteException(category + ", because it has sub categories");
        }
        if (!category.getProducts().isEmpty()) {
            throw new DeleteException(category + ", because it has products");
        }
    }

    @NonNull
    public List<CategoryDto> findAll(
            @NonNull final String name,
            @NonNull final Pageable pageable
    ) {
        return service.findAll(name, pageable)
                .stream()
                .map(converter::map)
                .toList();
    }

    @NonNull
    public Category get(@NonNull final Long id) {
        return service.find(id);
    }

    @NonNull
    private Category get(@NonNull final CategoryDto d) {
        return get(d.getId());
    }

    @NonNull
    public CategoryDto update(@NonNull final CategoryDto dto) {
        var m = converter.merge(dto, get(dto));
        var saved = service.save(m);
        return converter.map(saved);
    }

    @NonNull
    public void delete(@NonNull final Long id) {
        var m = get(id);
        checkForDelete(m);
        service.delete(m);
    }

    @NonNull
    public CategoryDto find(@NonNull final Long id) {
        return converter.map(service.find(id));
    }

    @NonNull
    public List<CategoryDto> findAll(@NonNull final Pageable pageable) {
        return service.findAll(pageable).stream().map(converter::map).toList();
    }

}

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
    public CategoryDto create(@NonNull CategoryDto categoryDto) {
        final Category converted = converter.map(categoryDto);
        final Category saved = service.save(converted);
        return converter.map(saved);
    }

    protected void checkForDelete(@NonNull Category category) {
        if (!category.getSubCategories().isEmpty()) {
            throw new DeleteException("Category " + category + " cannot be deleted because it has subcategories");
        }
        if (!category.getProducts().isEmpty()) {
            throw new DeleteException("Category " + category + " cannot be deleted because it has products");
        }
    }

    @NonNull
    public List<CategoryDto> findAll(
            @NonNull String name,
            @NonNull Pageable pageable
    ) {
        return service.findAll(name, pageable)
                .stream()
                .map(converter::map)
                .toList();
    }

    @NonNull
    public Category get(@NonNull Long id) {
        return service.find(id);
    }

    @NonNull
    private Category get(@NonNull CategoryDto d) {
        return get(d.getId());
    }

    @NonNull
    public CategoryDto update(@NonNull CategoryDto dto) {
        var m = converter.merge(dto, get(dto));
        var saved = service.save(m);
        return converter.map(saved);
    }

    @NonNull
    public void delete(@NonNull Long id) {
        var m = get(id);
        checkForDelete(m);
        service.delete(m);
    }

    @NonNull
    public CategoryDto find(@NonNull Long id) {
        return converter.map(service.find(id));
    }

    @NonNull
    public List<CategoryDto> findAll(@NonNull Pageable pageable) {
        return service.findAll(pageable).stream().map(converter::map).toList();
    }

}

package vlaship.backoffice.facade.impl;

import org.springframework.lang.NonNull;
import vlaship.backoffice.dto.CategoryDto;
import vlaship.backoffice.exception.DeleteException;
import vlaship.backoffice.model.Category;
import vlaship.backoffice.facade.AbstractFacade;
import vlaship.backoffice.mapper.impl.CategoryMapper;
import vlaship.backoffice.service.impl.CategoryService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryFacade extends AbstractFacade<Category, CategoryDto> {

    private final CategoryService categoryService;
    private final CategoryMapper categoryConverter;

    @NonNull
    public CategoryDto create(@NonNull final CategoryDto categoryDto) {
        final Category converted = categoryConverter.map(categoryDto);
        final Category saved = categoryService.save(converted);
        return categoryConverter.map(saved);
    }

    @NonNull
    @Override
    protected void checkForDelete(@NonNull final Category category) {
        if (!category.getSubCategories().isEmpty()) {
            throw new DeleteException(category + ", because it has sub categories");
        }
        if (!category.getProducts().isEmpty()) {
            throw new DeleteException(category + ", because it has products");
        }
    }

    @NonNull
    public List<CategoryDto> findAll(final Pageable pageable, final String name) {
        return categoryService.findAll(pageable, name)
                .stream()
                .map(categoryConverter::map)
                .collect(Collectors.toList());
    }

    public CategoryFacade(final CategoryMapper categoryConverter, final CategoryService categoryService) {
        super(categoryConverter, categoryService);
        this.categoryService = categoryService;
        this.categoryConverter = categoryConverter;
    }

}

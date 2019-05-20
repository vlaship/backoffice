package vlaship.backoffice.facade.impl;

import vlaship.backoffice.dto.CategoryDto;
import vlaship.backoffice.exception.DeleteException;
import vlaship.backoffice.model.Category;
import vlaship.backoffice.facade.AbstractFacade;
import vlaship.backoffice.facade.converter.impl.CategoryConverter;
import vlaship.backoffice.service.impl.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryFacade extends AbstractFacade<Category, CategoryDto> {

    private final CategoryService categoryService;
    private final CategoryConverter categoryConverter;

    public CategoryDto create(final CategoryDto categoryDto) {
        final Category converted = categoryConverter.convert(categoryDto);
        final Category saved = categoryService.save(converted);
        return categoryConverter.convert(saved);
    }

    @Override
    protected void checkForDelete(final Category category) {
        if (!category.getSubCategories().isEmpty()) {
            throw new DeleteException(category + ", because it has sub categories");
        }
        if (!category.getProducts().isEmpty()) {
            throw new DeleteException(category + ", because it has products");
        }
    }

    public List<CategoryDto> findAll(final Pageable pageable, final String name) {
        return categoryService.findAll(pageable, name).stream()
                .map(categoryConverter::convert)
                .collect(Collectors.toList());
    }

    @Autowired
    public CategoryFacade(final CategoryConverter categoryConverter,
                          final CategoryService categoryService) {
        super(categoryConverter, categoryService);
        this.categoryService = categoryService;
        this.categoryConverter = categoryConverter;
    }

}

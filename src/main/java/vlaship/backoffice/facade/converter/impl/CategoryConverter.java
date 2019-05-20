package vlaship.backoffice.facade.converter.impl;

import vlaship.backoffice.dto.CategoryDto;
import vlaship.backoffice.model.Category;
import vlaship.backoffice.model.Product;
import vlaship.backoffice.facade.converter.IConverter;
import vlaship.backoffice.service.impl.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryConverter implements IConverter<Category, CategoryDto> {

    private final CategoryService categoryService;

    @Autowired
    public CategoryConverter(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public CategoryDto convert(final Category category) {
        isNull(category, Category.class.getSimpleName());

        CategoryDto dto = new CategoryDto();

        dto.setId(category.getId());
        dto.setName(category.getName());

        if (category.getParent() != null) {
            dto.setParentId(category.getParent().getId());
        }

        final List<Integer> subCategories = category.getSubCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toList());
        dto.setSubCategories(subCategories);

        final List<Integer> productList = category.getProducts().stream()
                .map(Product::getId)
                .collect(Collectors.toList());
        dto.setProducts(productList);

        return dto;
    }

    public Category convert(final CategoryDto categoryDto) {
        isNull(categoryDto, CategoryDto.class.getSimpleName());

        final Category parentCategory = getParentCategory(categoryDto.getParentId());

        Category category = new Category();

        category.setName(categoryDto.getName());
        category.setParent(parentCategory);

        return category;
    }

    private Category getParentCategory(final Integer parentId) {
        if (parentId == null) {
            return null;
        }

        return categoryService.find(parentId);
    }

    @Override
    public Category convert(final CategoryDto categoryDto, final Category category) {

        isNull(category, Category.class.getSimpleName());
        isNull(categoryDto, CategoryDto.class.getSimpleName());

        final Category parentCategory = getParentCategory(categoryDto.getParentId());

        category.setName(categoryDto.getName());
        category.setParent(parentCategory);

        return category;
    }
}

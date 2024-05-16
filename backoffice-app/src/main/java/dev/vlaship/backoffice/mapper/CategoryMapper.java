package dev.vlaship.backoffice.mapper;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import dev.vlaship.backoffice.dto.CategoryDto;
import dev.vlaship.backoffice.model.Category;
import dev.vlaship.backoffice.model.Product;
import dev.vlaship.backoffice.service.impl.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {

    private final CategoryService categoryService;

    @Autowired
    public CategoryMapper(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @NonNull
    public CategoryDto map(@NonNull final Category category) {
        var subCategories = category.getSubCategories()
                .stream()
                .map(Category::getId)
                .toList();
        var products = category.getProducts()
                .stream()
                .map(Product::getId)
                .toList();

        var builder = new CategoryDto()
                .id(category.getId())
                .name(category.getName())
                .subCategories(subCategories)
                .products(products);

        if (category.getParent() != null) {
            builder.parentId(category.getParent().getId());
        }

        return builder;
    }

    @NonNull
    public Category map(@NonNull final CategoryDto categoryDto) {
        var parentCategory = getParentCategory(categoryDto.getParentId());

        return Category.builder()
                .name(categoryDto.getName())
                .parent(parentCategory)
                .build();
    }

    @Nullable
    private Category getParentCategory(@Nullable final Long parentId) {
        if (parentId == null) {
            return null;
        }

        return categoryService.find(parentId);
    }

    @NonNull
    public Category merge(@NonNull final CategoryDto categoryDto, @NonNull final Category category) {
        var parentCategory = getParentCategory(categoryDto.getParentId());

        category.setName(categoryDto.getName());
        category.setParent(parentCategory);

        return category;
    }

}

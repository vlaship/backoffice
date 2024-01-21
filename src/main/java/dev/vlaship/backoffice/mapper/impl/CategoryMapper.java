package dev.vlaship.backoffice.mapper.impl;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import dev.vlaship.backoffice.dto.CategoryDto;
import dev.vlaship.backoffice.model.Category;
import dev.vlaship.backoffice.model.Product;
import dev.vlaship.backoffice.mapper.BackOfficeMapper;
import dev.vlaship.backoffice.service.impl.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper implements BackOfficeMapper<Category, CategoryDto> {

    private final CategoryService categoryService;

    @Autowired
    public CategoryMapper(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @NonNull
    @Override
    public CategoryDto map(@NonNull final Category category) {
        var subCategories = category.getSubCategories()
                .stream()
                .map(Category::getId)
                .toList();
        var products = category.getProducts()
                .stream()
                .map(Product::getId)
                .toList();

        var builder = CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .subCategories(subCategories)
                .products(products);

        if (category.getParent() != null) {
            builder.parentId(category.getParent().getId());
        }

        return builder.build();
    }

    @NonNull
    public Category map(@NonNull final CategoryDto categoryDto) {
        var parentCategory = getParentCategory(categoryDto.parentId());

        return Category.builder()
                .name(categoryDto.name())
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
    @Override
    public Category merge(@NonNull final CategoryDto categoryDto, @NonNull final Category category) {
        var parentCategory = getParentCategory(categoryDto.parentId());

        category.setName(categoryDto.name());
        category.setParent(parentCategory);

        return category;
    }

}

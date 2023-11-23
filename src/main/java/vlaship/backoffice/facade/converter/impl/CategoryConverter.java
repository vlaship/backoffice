package vlaship.backoffice.facade.converter.impl;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import vlaship.backoffice.dto.CategoryDto;
import vlaship.backoffice.model.Category;
import vlaship.backoffice.model.Product;
import vlaship.backoffice.facade.converter.BackOfficeConverter;
import vlaship.backoffice.service.impl.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryConverter implements BackOfficeConverter<Category, CategoryDto> {

	private final CategoryService categoryService;

	@Autowired
	public CategoryConverter(final CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@NonNull
	@Override
	public CategoryDto convert(@NonNull final Category category) {
		CategoryDto dto = new CategoryDto();

		dto.setId(category.getId());
		dto.setName(category.getName());

		if (category.getParent() != null) {
			dto.setParentId(category.getParent().getId());
		}

		final List<Integer> subCategories = category.getSubCategories()
			.stream()
			.map(Category::getId)
			.collect(Collectors.toList());
		dto.setSubCategories(subCategories);

		final List<Integer> productList = category.getProducts()
			.stream()
			.map(Product::getId)
			.collect(Collectors.toList());
		dto.setProducts(productList);

		return dto;
	}

	@NonNull
	public Category convert(@NonNull final CategoryDto categoryDto) {
		final Category parentCategory = getParentCategory(categoryDto.getParentId());

		Category category = new Category();

		category.setName(categoryDto.getName());
		category.setParent(parentCategory);

		return category;
	}

	@Nullable
	private Category getParentCategory(@Nullable final Integer parentId) {
		if (parentId == null) {
			return null;
		}

		return categoryService.find(parentId);
	}

	@NonNull
	@Override
	public Category convert(@NonNull final CategoryDto categoryDto, @NonNull final Category category) {
		final Category parentCategory = getParentCategory(categoryDto.getParentId());

		category.setName(categoryDto.getName());
		category.setParent(parentCategory);

		return category;
	}

}

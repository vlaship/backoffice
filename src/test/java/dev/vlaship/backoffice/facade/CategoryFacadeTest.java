package dev.vlaship.backoffice.facade;

import org.junit.jupiter.api.Test;
import dev.vlaship.backoffice.dto.CategoryDto;
import dev.vlaship.backoffice.mapper.impl.CategoryMapper;
import dev.vlaship.backoffice.facade.impl.CategoryFacade;
import dev.vlaship.backoffice.model.Category;
import dev.vlaship.backoffice.model.Product;
import dev.vlaship.backoffice.repository.CategoryRepository;
import dev.vlaship.backoffice.service.impl.CategoryService;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.mock;

 class CategoryFacadeTest {

	private static final String NAME = "name";

	private CategoryRepository categoryRepository = mock(CategoryRepository.class);

	private CategoryFacade testSubject = new CategoryFacade(
			new CategoryMapper(new CategoryService(categoryRepository)), new CategoryService(categoryRepository));

	@Test
	 void test_create() {

		final Category category = new Category(NAME);
		category.setId(1l);

		final CategoryDto sourceDto = CategoryDto.builder().name(NAME).build();
		then(sourceDto.id()).isNull();

		Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(category);

		final CategoryDto dto = testSubject.create(sourceDto);
		then(dto.name()).isEqualTo(sourceDto.name());
		then(dto.id()).isEqualTo(category.getId());
	}

//	@Test(expected = DeleteException.class)
	 void test_delete_has_subCategory() {
		final Category category = new Category();
		category.getSubCategories().add(new Category());

		Mockito.when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(category));
		testSubject.delete(0L);
	}

//	@Test(expected = DeleteException.class)
	 void test_delete_has_Product() {
		final Category category = new Category();
		category.getProducts().add(new Product());

		Mockito.when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(category));
		testSubject.delete(0L);
	}

//	@Test(expected = NotFoundException.class)
	 void test_delete_has_NFE() {
		final Category category = new Category();
		category.getProducts().add(new Product());

		Mockito.when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		testSubject.delete(0L);
	}

	// @Test
	// public void test_find() {
	// Mockito.when(repository.findByName(Mockito.anyString())).thenReturn(Optional.of(new
	// Category(NAME)));
	// final CategoryDto category = testSubject.find(NAME);
	// then(categoryname()).isEqualTo(NAME);
	// }
	//
	// @Test(expected = SameNameException.class)
	// public void test_checkForSameName() {
	// Mockito.when(repository.findByName(Mockito.anyString())).thenReturn(Optional.of(new
	// Category()));
	// testSubject.update(CategoryDto.builder().name(NAME).build());
	// }

}

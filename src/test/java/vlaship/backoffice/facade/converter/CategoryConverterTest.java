package vlaship.backoffice.facade.converter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import vlaship.backoffice.dto.CategoryDto;
import vlaship.backoffice.exception.NotFoundException;
import vlaship.backoffice.facade.converter.impl.CategoryConverter;
import vlaship.backoffice.model.Category;
import vlaship.backoffice.model.Product;
import vlaship.backoffice.repository.CategoryRepository;
import vlaship.backoffice.service.impl.CategoryService;
import org.mockito.Mockito;

import java.util.*;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.mock;

public class CategoryConverterTest {

	private CategoryRepository categoryRepository = mock(CategoryRepository.class);

	private CategoryConverter testSubject = new CategoryConverter(new CategoryService(categoryRepository));

	private static List<Category> models;

	private static List<CategoryDto> dtos;

	@BeforeAll
	public static void setupCategoryDtos() {

		dtos = new ArrayList<>(Arrays.asList(CategoryDto.builder().id(1).name("category_0").build(),
				CategoryDto.builder().id(2).name("category_1").parentId(1).build(),
				CategoryDto.builder().id(3).name("category_2").parentId(1).build(),
				CategoryDto.builder().id(4).name("category_3").parentId(2).build(),
				CategoryDto.builder().id(5).name("category_4").parentId(2).build(),
				CategoryDto.builder().id(6).name("category_5").parentId(3).build(),
				CategoryDto.builder().id(7).name("category_6").parentId(3).build()));
	}

	@BeforeAll
	public static void setupCategories() {

		models = new ArrayList<>();

		final Category category0 = new Category("category_0");
		final Category category1 = new Category("category_1");
		final Category category2 = new Category("category_2");
		final Category category3 = new Category("category_3");
		final Category category4 = new Category("category_4");
		final Category category5 = new Category("category_5");
		final Category category6 = new Category("category_6");

		models.add(category0);
		models.add(category1);
		models.add(category2);
		models.add(category3);
		models.add(category4);
		models.add(category5);
		models.add(category6);

		category0.setId(1);
		category1.setId(2);
		category2.setId(3);
		category3.setId(4);
		category4.setId(5);
		category5.setId(6);
		category6.setId(7);

		category0.getSubCategories().add(category1);
		category0.getSubCategories().add(category2);

		category1.getSubCategories().add(category3);
		category1.getSubCategories().add(category4);

		category2.getSubCategories().add(category5);
		category2.getSubCategories().add(category6);

		category1.setParent(category0);
		category2.setParent(category0);

		category3.setParent(category1);
		category4.setParent(category1);

		category5.setParent(category2);
		category6.setParent(category2);

		for (int i = 0; i < 7; i++) {
			final Product product1 = new Product();
			final Product product2 = new Product();
			product1.setName("prod");
			product2.setName("prod");
			product1.setId(i);
			product2.setId(i + 7);
			models.get(i).getProducts().add(product1);
			models.get(i).getProducts().add(product2);
		}
	}

	@Test
	public void test_convert_CategoryToCategoryDto() {

		for (int i = 0; i < 7; i++) {
			final Category model = models.get(i);
			final CategoryDto dto = testSubject.convert(model);

			then(dto.getId()).isEqualTo(model.getId());
			then(dto.getName()).isEqualTo(model.getName());

			if (dto.getParentId() != null) {
				then(dto.getParentId()).isEqualTo(model.getParent().getId());
			}

			then(dto.getSubCategories().size()).isEqualTo(model.getSubCategories().size());

			if (!dto.getSubCategories().isEmpty()) {
				then(dto.getSubCategories().get(0)).isEqualTo(model.getSubCategories().get(0).getId());
				then(dto.getSubCategories().get(1)).isEqualTo(model.getSubCategories().get(1).getId());
			}

			then(dto.getProducts().size()).isEqualTo(model.getProducts().size());
			then(dto.getProducts().get(0)).isEqualTo(model.getProducts().get(0).getId());
			then(dto.getProducts().get(1)).isEqualTo(model.getProducts().get(1).getId());
		}
	}

//	@Test(expected = NotFoundException.class)
	public void test_convert_CategoryDtoToCategory_NFE() {
		Mockito.when(categoryRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		testSubject.convert(CategoryDto.builder().parentId(1).build());
	}

	@Test
	public void test_convert_CategoryDtoToCategory() {
		for (int i = 0; i < 7; i++) {
			Mockito.when(categoryRepository.findById(i + 1)).thenReturn(Optional.ofNullable(models.get(i)));
		}
		for (int i = 0; i < 7; i++) {
			final Category model = testSubject.convert(dtos.get(i));
			then(model.getName()).isEqualTo(dtos.get(i).getName());
			if (model.getParent() != null) {
				then(model.getParent().getId()).isEqualTo(dtos.get(i).getParentId());
			}
		}
	}

	@Test
	public void test_convert_CategoryDtoCategoryToCategory() {

		for (int i = 0; i < 7; i++) {
			Mockito.when(categoryRepository.findById(i + 1)).thenReturn(Optional.ofNullable(models.get(i)));
		}
		for (int i = 0; i < 7; i++) {
			final Category model = testSubject.convert(dtos.get(i), models.get(i));

			then(model.getId()).isEqualTo(models.get(i).getId());
			then(model.getName()).isEqualTo(dtos.get(i).getName());
			then(model.getName()).isEqualTo(models.get(i).getName());

			if (model.getParent() != null) {
				then(model.getParent().getId()).isEqualTo(dtos.get(i).getParentId());
			}
		}
	}

}

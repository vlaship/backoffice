package dev.vlaship.backoffice.facade.converter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import dev.vlaship.backoffice.dto.CategoryDto;
import dev.vlaship.backoffice.mapper.CategoryMapper;
import dev.vlaship.backoffice.model.Category;
import dev.vlaship.backoffice.model.Product;
import dev.vlaship.backoffice.repository.CategoryRepository;
import dev.vlaship.backoffice.service.impl.CategoryService;
import org.mockito.Mockito;

import java.util.*;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.mock;

public class CategoryConverterTest {

    private CategoryRepository categoryRepository = mock(CategoryRepository.class);

    private CategoryMapper testSubject = new CategoryMapper(new CategoryService(categoryRepository));

    private static List<Category> models;

    private static List<CategoryDto> dtos;

    @BeforeAll
    public static void setupCategoryDtos() {

        dtos = List.of(CategoryDto.builder().id(1l).name("category_0").build(),
                CategoryDto.builder().id(2l).name("category_1").parentId(1l).build(),
                CategoryDto.builder().id(3l).name("category_2").parentId(1l).build(),
                CategoryDto.builder().id(4l).name("category_3").parentId(2l).build(),
                CategoryDto.builder().id(5l).name("category_4").parentId(2l).build(),
                CategoryDto.builder().id(6l).name("category_5").parentId(3l).build(),
                CategoryDto.builder().id(7l).name("category_6").parentId(3l).build()
        );
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

        category0.setId(1l);
        category1.setId(2l);
        category2.setId(3l);
        category3.setId(4l);
        category4.setId(5l);
        category5.setId(6l);
        category6.setId(7l);

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

        for (long i = 0; i < 7; i++) {
            final Product product1 = new Product();
            final Product product2 = new Product();
            product1.setName("prod");
            product2.setName("prod");
            product1.setId(i);
            product2.setId(i + 7);
            models.get((int) i).getProducts().add(product1);
            models.get((int) i).getProducts().add(product2);
        }
    }

    @Test
    public void test_convert_CategoryToCategoryDto() {

        for (int i = 0; i < 7; i++) {
            final Category model = models.get(i);
            final CategoryDto dto = testSubject.map(model);

            then(dto.id()).isEqualTo(model.getId());
            then(dto.name()).isEqualTo(model.getName());

            if (dto.parentId() != null) {
                then(dto.parentId()).isEqualTo(model.getParent().getId());
            }

            then(dto.subCategories().size()).isEqualTo(model.getSubCategories().size());

            if (!dto.subCategories().isEmpty()) {
                then(dto.subCategories().get(0)).isEqualTo(model.getSubCategories().get(0).getId());
                then(dto.subCategories().get(1)).isEqualTo(model.getSubCategories().get(1).getId());
            }

            then(dto.products().size()).isEqualTo(model.getProducts().size());
            then(dto.products().get(0)).isEqualTo(model.getProducts().get(0).getId());
            then(dto.products().get(1)).isEqualTo(model.getProducts().get(1).getId());
        }
    }

    //	@Test(expected = NotFoundException.class)
    public void test_convert_CategoryDtoToCategory_NFE() {
        Mockito.when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        testSubject.map(CategoryDto.builder().parentId(1l).build());
    }

    @Test
    public void test_convert_CategoryDtoToCategory() {
        for (int i = 0; i < 7; i++) {
            Mockito.when(categoryRepository.findById(i + 1l)).thenReturn(Optional.ofNullable(models.get(i)));
        }
        for (int i = 0; i < 7; i++) {
            final Category model = testSubject.map(dtos.get(i));
            then(model.getName()).isEqualTo(dtos.get(i).name());
            if (model.getParent() != null) {
                then(model.getParent().getId()).isEqualTo(dtos.get(i).parentId());
            }
        }
    }

    @Test
    public void test_convert_CategoryDtoCategoryToCategory() {

        for (int i = 0; i < 7; i++) {
            Mockito.when(categoryRepository.findById(i + 1l)).thenReturn(Optional.ofNullable(models.get(i)));
        }
        for (int i = 0; i < 7; i++) {
            final Category model = testSubject.merge(dtos.get(i), models.get(i));

            then(model.getId()).isEqualTo(models.get(i).getId());
            then(model.getName()).isEqualTo(dtos.get(i).name());
            then(model.getName()).isEqualTo(models.get(i).getName());

            if (model.getParent() != null) {
                then(model.getParent().getId()).isEqualTo(dtos.get(i).parentId());
            }
        }
    }

}

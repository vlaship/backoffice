package vlaship.backoffice.facade;

import vlaship.backoffice.dto.CategoryDto;
import vlaship.backoffice.exception.DeleteException;
import vlaship.backoffice.exception.NotFoundException;
import vlaship.backoffice.facade.converter.impl.CategoryConverter;
import vlaship.backoffice.facade.impl.CategoryFacade;
import vlaship.backoffice.model.Category;
import vlaship.backoffice.model.Product;
import vlaship.backoffice.repository.CategoryRepository;
import vlaship.backoffice.service.impl.CategoryService;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.mock;

public class CategoryFacadeTest {

    private static final String NAME = "name";

    private CategoryRepository categoryRepository = mock(CategoryRepository.class);
    private CategoryFacade testSubject = new CategoryFacade(
            new CategoryConverter(new CategoryService(categoryRepository)),
            new CategoryService(categoryRepository)
    );

    @Test
    public void test_create() {

        final Category category = new Category(NAME);
        category.setId(1);

        final CategoryDto sourceDto = CategoryDto.builder().name(NAME).build();
        then(sourceDto.getId()).isNull();

        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(category);

        final CategoryDto dto = testSubject.create(sourceDto);
        then(dto.getName()).isEqualTo(sourceDto.getName());
        then(dto.getId()).isEqualTo(category.getId());
    }

    @Test(expected = DeleteException.class)
    public void test_delete_has_subCategory() {
        final Category category = new Category();
        category.getSubCategories().add(new Category());

        Mockito.when(categoryRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(category));
        testSubject.delete(0);
    }

    @Test(expected = DeleteException.class)
    public void test_delete_has_Product() {
        final Category category = new Category();
        category.getProducts().add(new Product());

        Mockito.when(categoryRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(category));
        testSubject.delete(0);
    }

    @Test(expected = NotFoundException.class)
    public void test_delete_has_NFE() {
        final Category category = new Category();
        category.getProducts().add(new Product());

        Mockito.when(categoryRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        testSubject.delete(0);
    }

//    @Test
//    public void test_find() {
//        Mockito.when(repository.findByName(Mockito.anyString())).thenReturn(Optional.of(new Category(NAME)));
//        final CategoryDto category = testSubject.find(NAME);
//        then(category.getName()).isEqualTo(NAME);
//    }
//
//    @Test(expected = SameNameException.class)
//    public void test_checkForSameName() {
//        Mockito.when(repository.findByName(Mockito.anyString())).thenReturn(Optional.of(new Category()));
//        testSubject.update(CategoryDto.builder().name(NAME).build());
//    }
}
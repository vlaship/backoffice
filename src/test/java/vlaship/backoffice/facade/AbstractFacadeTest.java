package vlaship.backoffice.facade;

import vlaship.backoffice.dto.CategoryDto;
import vlaship.backoffice.mapper.impl.CategoryMapper;
import vlaship.backoffice.facade.impl.CategoryFacade;
import vlaship.backoffice.model.Category;
import vlaship.backoffice.service.impl.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import vlaship.backoffice.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.mock;

class AbstractFacadeTest {

    private CategoryRepository categoryRepository = mock(CategoryRepository.class);

    private CategoryFacade testSubject = new CategoryFacade(
            new CategoryMapper(new CategoryService(categoryRepository)), new CategoryService(categoryRepository));

    // @Test(expected = NotFoundException.class)
    // public void get() {
    // Mockito.when(repository.findById(Mockito.anyInt())).thenThrow(new
    // NotFoundException("", ""));
    // testSubject.get(1);
    // }

    // @Test
    // public void test_update() {
    // final String name = "name";
    //
    // final Category old = new Category("old");
    // final Category saved = new Category(name);
    // saved.setId(1);
    //
    // Mockito.when(repository.findByName(Mockito.anyString())).thenReturn(Optional.empty());
    // Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(old));
    // Mockito.when(repository.save(Mockito.any(Category.class))).thenReturn(saved);
    //
    // final CategoryDto updated =
    // testSubject.update(CategoryDto.builder().id(1).name(name).build());
    //
    // then(updated.getName()).isEqualTo(name);
    // }

    @Test
    void test_findAll() {
        final Category category1 = new Category("1");
        final Category category2 = new Category("2");

        final Page page = new PageImpl<>(Arrays.asList(category1, category2));

        Mockito.when(categoryRepository.findAll(Mockito.any(Pageable.class))).thenReturn(page);

        final List<CategoryDto> dtos = testSubject.findAll(PageRequest.of(1, 1));

        then(dtos.get(0).name()).isEqualTo(category1.getName());
        then(dtos.get(1).name()).isEqualTo(category2.getName());
    }

}

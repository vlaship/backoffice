package dev.vlaship.backoffice.repository;

import dev.vlaship.backoffice.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@DataJpaTest
class CategoryRepositoryTest {

    private static final String CATEGORY = "category";

    @Autowired
    private CategoryRepository repository;

    @Test
    void test_findByName() {
        repository.save(new Category(CATEGORY));
        final List<Category> categories = repository.findAllByName(CATEGORY, PageRequest.of(0, 5));
        then(categories.get(0).getName()).isEqualTo(CATEGORY);
    }

}

package vlaship.backoffice.repository;

import vlaship.backoffice.model.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryRepositoryTest {

    private static final String CATEGORY = "category";
    @Autowired
    private CategoryRepository repository;

    @Test
    public void test_findByName() {
        repository.save(new Category(CATEGORY));
        final List<Category> categories = repository.findAllByName(CATEGORY, PageRequest.of(0, 5));
        then(categories.get(0).getName()).isEqualTo(CATEGORY);
    }
}
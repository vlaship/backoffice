package vlaship.backoffice.repository;

import vlaship.backoffice.model.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    List<Category> findAllByName(String name, Pageable pageable);
}

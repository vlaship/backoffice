package dev.vlaship.backoffice.repository;

import dev.vlaship.backoffice.model.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByName(String name, Pageable pageable);

}

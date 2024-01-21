package dev.vlaship.backoffice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import dev.vlaship.backoffice.model.Category;
import dev.vlaship.backoffice.repository.CategoryRepository;
import dev.vlaship.backoffice.service.AbstractService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class CategoryService extends AbstractService<Category> {

    private final CategoryRepository repository;

    @NonNull
    public List<Category> findAll(
            @NonNull final String name,
            @NonNull final Pageable pageable
    ) {
        return repository.findAllByName(name, pageable);
    }

    public CategoryService(final CategoryRepository repository) {
        super(repository);
        this.repository = repository;
        setTypeClass(Category.class);
    }

}

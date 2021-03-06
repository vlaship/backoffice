package vlaship.backoffice.service.impl;

import vlaship.backoffice.model.Category;
import vlaship.backoffice.repository.CategoryRepository;
import vlaship.backoffice.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryService extends AbstractService<Category> {

    private final CategoryRepository repository;

    public List<Category> findAll(final Pageable pageable, final String name) {
        return repository.findAllByName(name, pageable);
    }

    @Autowired
    public CategoryService(final CategoryRepository repository) {
        super(repository);
        this.repository = repository;
        setTypeClass(Category.class);
    }
}

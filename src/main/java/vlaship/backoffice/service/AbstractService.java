package vlaship.backoffice.service;

import vlaship.backoffice.exception.BadRequestException;
import vlaship.backoffice.exception.NotFoundException;
import vlaship.backoffice.model.IModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

import java.util.List;

public abstract class AbstractService<M extends IModel> implements IService<M> {

    @Getter
    @Setter
    private Class<M> typeClass;

    private final JpaRepository<M, Integer> repository;

    @Override
    public M find(final Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(typeClass.getSimpleName(), id));
    }

    @Override
    public M get(final Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new BadRequestException(typeClass.getSimpleName(), id));
    }

    @Override
    public List<M> findAll(final Pageable pageable) {
        return repository.findAll(pageable).getContent();
    }

    @Override
    public void delete(final M m) {
        repository.delete(m);
    }

    @Override
    public M save(final M m) {
        return repository.save(m);
    }

    protected AbstractService(final JpaRepository<M, Integer> repository) {
        this.repository = repository;
    }
}

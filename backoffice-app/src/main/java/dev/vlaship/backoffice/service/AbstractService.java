package dev.vlaship.backoffice.service;

import org.springframework.lang.NonNull;
import dev.vlaship.backoffice.exception.BadRequestException;
import dev.vlaship.backoffice.exception.NotFoundException;
import dev.vlaship.backoffice.model.Model;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

import java.util.List;

public abstract class AbstractService<M extends Model> implements BackOfficeService<M> {

    @Getter
    @Setter
    private Class<M> typeClass;

    private final JpaRepository<M, Long> repository;

    @NonNull
    @Override
    public M find(@NonNull Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(typeClass.getSimpleName(), id));
    }

    @NonNull
    @Override
    public M get(@NonNull Long id) {
        return repository.findById(id).orElseThrow(() -> new BadRequestException(typeClass.getSimpleName(), id));
    }

    @NonNull
    @Override
    public List<M> findAll(@NonNull Pageable pageable) {
        return repository.findAll(pageable).getContent();
    }

    @Override
    public void delete(@NonNull M m) {
        repository.delete(m);
    }

    @NonNull
    @Override
    public M save(@NonNull M m) {
        return repository.save(m);
    }

    protected AbstractService(final JpaRepository<M, Long> repository) {
        this.repository = repository;
    }

}

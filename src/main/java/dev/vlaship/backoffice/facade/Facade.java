package dev.vlaship.backoffice.facade;

import org.springframework.lang.NonNull;
import dev.vlaship.backoffice.dto.Dto;
import dev.vlaship.backoffice.model.Model;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface Facade<M extends Model, D extends Dto> {

    @NonNull
    D find(@NonNull Long id);

    @NonNull
    List<D> findAll(@NonNull Pageable pageable);

    @NonNull
    D update(@NonNull D dto);

    void delete(@NonNull Long id);

    @NonNull
    M get(@NonNull Long id);

    // M get(D d);

}

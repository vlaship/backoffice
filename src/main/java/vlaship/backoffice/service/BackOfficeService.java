package vlaship.backoffice.service;

import org.springframework.lang.NonNull;
import vlaship.backoffice.model.Model;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BackOfficeService<M extends Model> {

    @NonNull
    M find(@NonNull Long id);

    @NonNull
    List<M> findAll(@NonNull Pageable pageable);

    void delete(@NonNull M m);

    @NonNull
    M save(@NonNull M m);

    @NonNull
    M get(@NonNull Long id);

}

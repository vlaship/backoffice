package vlaship.backoffice.facade;

import org.springframework.lang.NonNull;
import vlaship.backoffice.dto.Dto;
import vlaship.backoffice.model.Model;
import vlaship.backoffice.mapper.BackOfficeMapper;
import vlaship.backoffice.service.BackOfficeService;
import org.springframework.data.domain.Pageable;

import java.util.List;

public abstract class AbstractFacade<M extends Model, D extends Dto> implements Facade<M, D> {

    private final BackOfficeMapper<M, D> converter;

    private final BackOfficeService<M> service;

    @NonNull
    public M get(@NonNull final Long id) {
        return service.find(id);
    }

    @NonNull
    private M get(@NonNull final D d) {
        return get(d.id());
    }

    @NonNull
    @Override
    public D update(@NonNull final D dto) {
        M m = converter.merge(dto, get(dto));
        final M saved = service.save(m);
        return converter.map(saved);
    }

    @NonNull
    @Override
    public void delete(@NonNull final Long id) {
        final M m = get(id);
        checkForDelete(m);
        service.delete(m);
    }

    @NonNull
    @Override
    public D find(@NonNull final Long id) {
        return converter.map(service.find(id));
    }

    @NonNull
    @Override
    public List<D> findAll(@NonNull final Pageable pageable) {
        return service.findAll(pageable).stream().map(converter::map).toList();
    }

    protected abstract void checkForDelete(M m);

    protected AbstractFacade(final BackOfficeMapper<M, D> converter, final BackOfficeService<M> service) {
        this.converter = converter;
        this.service = service;
    }

}

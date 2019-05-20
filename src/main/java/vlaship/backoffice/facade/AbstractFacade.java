package vlaship.backoffice.facade;

import vlaship.backoffice.dto.IDto;
import vlaship.backoffice.exception.BadRequestException;
import vlaship.backoffice.model.IModel;
import vlaship.backoffice.facade.converter.IConverter;
import vlaship.backoffice.service.IService;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractFacade<M extends IModel, D extends IDto> implements Facade<M, D> {

    private final IConverter<M, D> converter;
    private final IService<M> service;

    public M get(final Integer id) {
        return service.find(id);
    }

    private M get(final D d) {
        if (d.getId() == null) {
            throw new BadRequestException(d.getClass().getSimpleName(), d);
        }
        return get(d.getId());
    }

    @Override
    public D update(final D dto) {
        M m = converter.convert(dto, get(dto));
        final M saved = service.save(m);
        return converter.convert(saved);
    }

    @Override
    public void delete(final Integer id) {
        final M m = get(id);
        checkForDelete(m);
        service.delete(m);
    }

    @Override
    public D find(final Integer id) {
        return converter.convert(service.find(id));
    }

    @Override
    public List<D> findAll(final Pageable pageable) {
        return service.findAll(pageable)
                .stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }

    protected abstract void checkForDelete(M m);

    public AbstractFacade(final IConverter<M, D> converter,
                          final IService<M> service) {
        this.converter = converter;
        this.service = service;
    }

}

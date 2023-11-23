package vlaship.backoffice.facade;

import org.springframework.lang.NonNull;
import vlaship.backoffice.dto.Dto;
import vlaship.backoffice.model.Model;
import vlaship.backoffice.facade.converter.BackOfficeConverter;
import vlaship.backoffice.service.BackOfficeService;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractFacade<M extends Model, D extends Dto> implements Facade<M, D> {

	private final BackOfficeConverter<M, D> converter;

	private final BackOfficeService<M> service;

	@NonNull
	public M get(@NonNull final Integer id) {
		return service.find(id);
	}

	@NonNull
	private M get(@NonNull final D d) {
		return get(d.getId());
	}

	@NonNull
	@Override
	public D update(@NonNull final D dto) {
		M m = converter.convert(dto, get(dto));
		final M saved = service.save(m);
		return converter.convert(saved);
	}

	@NonNull
	@Override
	public void delete(@NonNull final Integer id) {
		final M m = get(id);
		checkForDelete(m);
		service.delete(m);
	}

	@NonNull
	@Override
	public D find(@NonNull final Integer id) {
		return converter.convert(service.find(id));
	}

	@NonNull
	@Override
	public List<D> findAll(@NonNull final Pageable pageable) {
		return service.findAll(pageable).stream().map(converter::convert).collect(Collectors.toList());
	}

	protected abstract void checkForDelete(M m);

	public AbstractFacade(final BackOfficeConverter<M, D> converter, final BackOfficeService<M> service) {
		this.converter = converter;
		this.service = service;
	}

}

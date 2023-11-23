package vlaship.backoffice.facade;

import org.springframework.lang.NonNull;
import vlaship.backoffice.dto.Dto;
import vlaship.backoffice.model.Model;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface Facade<M extends Model, D extends Dto> {

	@NonNull
	D find(@NonNull Integer id);

	@NonNull
	List<D> findAll(@NonNull Pageable pageable);

	@NonNull
	D update(@NonNull D dto);

	void delete(@NonNull Integer id);

	@NonNull
	M get(@NonNull Integer id);

	// M get(D d);

}

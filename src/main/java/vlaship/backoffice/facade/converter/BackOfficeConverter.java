package vlaship.backoffice.facade.converter;

import org.springframework.lang.NonNull;
import vlaship.backoffice.dto.Dto;
import vlaship.backoffice.model.Model;

public interface BackOfficeConverter<M extends Model, D extends Dto> {

	@NonNull
	D convert(@NonNull M model);

	@NonNull
	M convert(@NonNull D dto, @NonNull M model);

}

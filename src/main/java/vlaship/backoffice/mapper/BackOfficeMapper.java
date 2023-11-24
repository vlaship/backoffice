package vlaship.backoffice.mapper;

import org.springframework.lang.NonNull;
import vlaship.backoffice.dto.Dto;
import vlaship.backoffice.model.Model;

public interface BackOfficeMapper<M extends Model, D extends Dto> {

    @NonNull
    D map(@NonNull M model);

    @NonNull
    M merge(@NonNull D dto, @NonNull M model);

}

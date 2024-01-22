package dev.vlaship.backoffice.mapper;

import org.springframework.lang.NonNull;
import dev.vlaship.backoffice.dto.Dto;
import dev.vlaship.backoffice.model.Model;

public interface BackOfficeMapper<M extends Model, D extends Dto> {

    @NonNull
    D map(@NonNull M model);

    @NonNull
    M merge(@NonNull D dto, @NonNull M model);

}

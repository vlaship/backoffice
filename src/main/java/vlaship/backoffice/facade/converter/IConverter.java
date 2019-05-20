package vlaship.backoffice.facade.converter;

import vlaship.backoffice.dto.IDto;
import vlaship.backoffice.model.IModel;

public interface IConverter<M extends IModel, D extends IDto> {

    D convert(M model);

    M convert(D dto, M model);

    default void isNull(Object obj, String className) {
        if (obj == null) {
            throw new IllegalStateException(className + " is null");
        }
    }
}

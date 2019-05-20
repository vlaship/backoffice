package vlaship.backoffice.exception;

import vlaship.backoffice.dto.IDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends AbstractException {

    public NotFoundException(final String model, final int id) {
        super("Can't find " + model + " with ID = " + id);
    }

    public NotFoundException(final String model, final IDto dto) {
        super("Can't find " + model + " = " + dto);
    }
}

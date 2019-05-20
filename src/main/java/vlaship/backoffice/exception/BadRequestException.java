package vlaship.backoffice.exception;

import vlaship.backoffice.dto.IDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends AbstractException {

    public BadRequestException(final String model, final int id) {
        super("Can't find " + model + " with ID = " + id);
    }

    public BadRequestException(final String model, final IDto dto) {
        super("Can't find " + model + " = " + dto);
    }

    public BadRequestException(final String message) {
        super(message);
    }

}

package vlaship.backoffice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DeleteException extends AbstractException {

    public DeleteException(final String message) {
        super("Can't delete " + message);
    }
}

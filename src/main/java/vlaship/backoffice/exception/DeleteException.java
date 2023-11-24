package vlaship.backoffice.exception;

public class DeleteException extends AbstractException {

    public DeleteException(final String message) {
        super("Can't delete " + message);
    }

}

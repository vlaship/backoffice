package vlaship.backoffice.exception;

public class BadRequestException extends AbstractException {

    public BadRequestException(final String model, final int id) {
        super("Can't find " + model + " with ID = " + id);
    }

}

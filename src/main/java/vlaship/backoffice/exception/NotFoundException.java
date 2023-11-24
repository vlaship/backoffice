package vlaship.backoffice.exception;

public class NotFoundException extends AbstractException {

    public NotFoundException(final String model, final long id) {
        super("Can't find " + model + " with ID = " + id);
    }

}

package vlaship.backoffice.exception;

public class NotFoundException extends AbstractException {

	public NotFoundException(final String model, final int id) {
		super("Can't find " + model + " with ID = " + id);
	}

}

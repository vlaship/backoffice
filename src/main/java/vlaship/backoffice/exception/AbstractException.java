package vlaship.backoffice.exception;

public abstract class AbstractException extends IllegalArgumentException {
    private final String message;

    AbstractException(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

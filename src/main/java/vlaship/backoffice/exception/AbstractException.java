package vlaship.backoffice.exception;

import lombok.Getter;

@Getter
public abstract class AbstractException extends IllegalArgumentException {

	private final String message;

	AbstractException(final String message) {
		this.message = message;
	}

}

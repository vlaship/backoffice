package dev.vlaship.backoffice.exception;

import lombok.Getter;

@Getter
public abstract class AbstractException extends IllegalArgumentException {
    AbstractException(final String message) {
        super(message);
    }
}

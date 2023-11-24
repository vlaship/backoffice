package vlaship.backoffice.exception;

public class JwtAuthenticationException extends AbstractException {
    public JwtAuthenticationException() {
        super("Token is invalid");
    }
}

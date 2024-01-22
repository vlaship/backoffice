package dev.vlaship.backoffice.client;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;

import java.net.URI;

public class ClientException extends RuntimeException {
    public ClientException(URI url, HttpMethod method, HttpStatusCode status, final String message) {
        super(message);
    }
}

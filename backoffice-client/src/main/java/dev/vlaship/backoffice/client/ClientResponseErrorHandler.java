package dev.vlaship.backoffice.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;
import java.net.URI;

@Slf4j
public class ClientResponseErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public void handleError(@NonNull URI url, @NonNull HttpMethod method, @NonNull ClientHttpResponse response) throws IOException {
        var status = response.getStatusCode();
        try (var is = response.getBody()) {
            var body = new String(is.readAllBytes());
            log.error("Error: {} {} {} {}", url, method, status, body);
            throw new ClientException(url, method, status, body);
        } catch (Exception e) {
            log.error("Error: {} {} {}", url, method, status, e);
            throw new ClientException(url, method, status, e.getMessage(), e);
        }
    }
}

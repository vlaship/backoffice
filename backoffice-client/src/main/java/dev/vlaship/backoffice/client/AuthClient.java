package dev.vlaship.backoffice.client;

import dev.vlaship.backoffice.api.AuthApi;
import dev.vlaship.backoffice.dto.LoginRequest;
import dev.vlaship.backoffice.dto.LoginResponse;
import dev.vlaship.backoffice.dto.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class AuthClient implements AuthApi {

    private final ClientProperties clientProperties;
    private final RestTemplate restTemplate;

    @Override
    public ResponseEntity<Void> signup(SignupRequest request) {
        var url = UriComponentsBuilder.fromUriString(clientProperties.baseUrl())
                .path(clientProperties.path())
                .path("/signup")
                .build()
                .toString();
        return RestClient.builder(restTemplate)
                .baseUrl(url)
                .build()
                .post()
                .body(request)
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest request) {
        var url = UriComponentsBuilder.fromUriString(clientProperties.baseUrl())
                .path(clientProperties.path())
                .path("/login")
                .build()
                .toString();
        return RestClient.builder(restTemplate)
                .baseUrl(url)
                .build()
                .post()
                .retrieve()
                .toEntity(LoginResponse.class);
    }
}

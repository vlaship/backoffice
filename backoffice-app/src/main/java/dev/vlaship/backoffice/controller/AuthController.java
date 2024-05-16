package dev.vlaship.backoffice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import dev.vlaship.backoffice.api.AuthApi;
import dev.vlaship.backoffice.dto.LoginRequest;
import dev.vlaship.backoffice.dto.LoginResponse;
import dev.vlaship.backoffice.dto.SignupRequest;
import dev.vlaship.backoffice.facade.UserFacade;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final UserFacade userFacade;

    @Override
    public ResponseEntity<Void> signup(SignupRequest request) {
        userFacade.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest request) {
        var resp = userFacade.login(request);
        return ResponseEntity.ok(resp);
    }
}

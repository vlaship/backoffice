package vlaship.backoffice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vlaship.backoffice.dto.LoginRequest;
import vlaship.backoffice.dto.LoginResponse;
import vlaship.backoffice.dto.SignupRequest;
import vlaship.backoffice.facade.UserFacade;

@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserFacade userFacade;

    @PostMapping(value = "/signup")
    public ResponseEntity<Object> signup(@RequestBody @Valid SignupRequest request) {
        userFacade.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        var resp = userFacade.login(request);
        return ResponseEntity.ok(resp);
    }

}

package vlaship.backoffice.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import vlaship.backoffice.dto.LoginRequest;
import vlaship.backoffice.dto.LoginResponse;
import vlaship.backoffice.dto.SignupRequest;
import vlaship.backoffice.facade.converter.UserConverter;
import vlaship.backoffice.service.UserService;

@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;
    private final UserConverter userConverter;

    public LoginResponse login(@NonNull LoginRequest req) {
        var token = userService.login(userConverter.convert(req));
        return userConverter.convert(token);
    }

    public void signup(SignupRequest request) {
        userService.signup(userConverter.convert(request));
    }
}

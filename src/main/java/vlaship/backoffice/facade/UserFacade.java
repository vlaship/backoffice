package vlaship.backoffice.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import vlaship.backoffice.dto.LoginRequest;
import vlaship.backoffice.dto.LoginResponse;
import vlaship.backoffice.dto.SignupRequest;
import vlaship.backoffice.mapper.UserMapper;
import vlaship.backoffice.service.UserService;

@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;
    private final UserMapper userMapper;

    public LoginResponse login(@NonNull LoginRequest req) {
        var token = userService.login(userMapper.convert(req));
        return userMapper.convert(token);
    }

    public void signup(SignupRequest request) {
        userService.signup(userMapper.convert(request));
    }
}

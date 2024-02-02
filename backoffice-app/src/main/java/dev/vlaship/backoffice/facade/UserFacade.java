package dev.vlaship.backoffice.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import dev.vlaship.backoffice.dto.LoginRequest;
import dev.vlaship.backoffice.dto.LoginResponse;
import dev.vlaship.backoffice.dto.SignupRequest;
import dev.vlaship.backoffice.mapper.UserMapper;
import dev.vlaship.backoffice.service.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;
    private final UserMapper userMapper;

    @NonNull
    public LoginResponse login(@NonNull LoginRequest req) {
        var token = userService.login(userMapper.convert(req));
        return userMapper.convert(token);
    }

    public void signup(@NonNull SignupRequest request) {
        userService.signup(userMapper.convert(request));
    }
}

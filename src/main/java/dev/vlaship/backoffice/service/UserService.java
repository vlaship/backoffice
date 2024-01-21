package dev.vlaship.backoffice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import dev.vlaship.backoffice.model.User;
import dev.vlaship.backoffice.repository.UserRepository;
import dev.vlaship.backoffice.security.JwtTokenUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @NonNull
    public String login(@NonNull User user) {
        var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));
        return jwtTokenUtil.generateToken(auth);
    }

    public void signup(@NonNull User user) {
        var encoded = passwordEncoder.encode(user.getPassword());
        user.setPassword(encoded);
        userRepository.save(user);
    }
}

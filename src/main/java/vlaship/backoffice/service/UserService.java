package vlaship.backoffice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vlaship.backoffice.model.User;
import vlaship.backoffice.repository.UserRepository;
import vlaship.backoffice.security.JwtTokenUtil;

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

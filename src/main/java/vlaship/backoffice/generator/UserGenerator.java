package vlaship.backoffice.generator;

import vlaship.backoffice.model.User;
import vlaship.backoffice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class UserGenerator {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserGenerator(final UserRepository userRepository, final PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public void login() {
        userRepository.save(new User("user", encoder.encode("123")));
    }
}

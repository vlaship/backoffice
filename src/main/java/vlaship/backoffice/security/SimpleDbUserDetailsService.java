package vlaship.backoffice.security;

import vlaship.backoffice.model.User;
import vlaship.backoffice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SimpleDbUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String name) {
        User user = userRepository.findByName(name).orElseThrow(() -> new UsernameNotFoundException(name));
        return new SimpleDbUserPrinciple(user);
    }

    @Autowired
    public SimpleDbUserDetailsService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}

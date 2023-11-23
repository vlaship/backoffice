package vlaship.backoffice.security;

import lombok.RequiredArgsConstructor;
import vlaship.backoffice.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SimpleDbUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(final String name) {
		var user = userRepository.findByName(name).orElseThrow(() -> new UsernameNotFoundException(name));
		return new SimpleDbUserPrinciple(user);
	}

}

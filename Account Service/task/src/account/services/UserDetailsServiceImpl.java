package account.services;

import account.domain.User;
import account.logging.Event;
import account.logging.Logger;
import account.repositories.UserRepository;
import account.security.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;
    private Logger logger;

    public UserDetailsServiceImpl(UserRepository userRepository, Logger logger) {
        this.userRepository = userRepository;
        this.logger = logger;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("Not found: " + username);
        }
        if (!user.isAccountNonLocked()) {
            throw new RuntimeException("User account is locked");
        }
        return new UserDetailsImpl(user);
    }
}

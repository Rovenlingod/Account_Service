package account.services;

import account.domain.Role;
import account.domain.User;
import account.logging.Event;
import account.logging.Logger;
import account.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@Transactional
public class LoginAttemptServiceImpl implements LoginAttemptService {

    private final int MAX_ATTEMPTS = 5;
    private UserRepository userRepository;
    private Logger logger;

    public LoginAttemptServiceImpl(UserRepository userRepository, Logger logger) {
        this.userRepository = userRepository;
        this.logger = logger;
    }

    @Override
    public void loginSucceeded(String email) {
        userRepository.clearFailedAttemptsIgnoreCase(email);
    }

    @Override
    public void loginFailed(String email) {
        userRepository.incFailedAttemptsIgnoreCase(email);
        User user = userRepository.findByEmailIgnoreCase(email);
        logger.logWithSubject(Event.LOGIN_FAILED, email.toLowerCase());
        if (Objects.isNull(user) || !user.isAccountNonLocked()) return;
        if (user.getFailedAttempts() >= 5 && user.getRoles().stream().noneMatch(Role::isAdministrative)) {
            user.setAccountNonLocked(false);
            userRepository.save(user);
            logger.logWithSubject(Event.BRUTE_FORCE, email.toLowerCase());
            logger.log(Event.LOCK_USER, email.toLowerCase(), "Lock user " + email.toLowerCase());
        }
    }

    @Override
    public boolean isBlocked(String email) {
        return userRepository.findAccountNonLockedByEmail(email);
    }
}

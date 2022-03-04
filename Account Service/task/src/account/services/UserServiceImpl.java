package account.services;

import account.domain.Role;
import account.domain.User;
import account.dtos.UserDTO;
import account.exceptions.InvalidCombinationOfRolesException;
import account.exceptions.InvalidUserDeletionRequestException;
import account.exceptions.RoleNotFoundException;
import account.exceptions.UserNotFoundException;
import account.logging.Event;
import account.logging.Logger;
import account.mappers.UserMapper;
import account.repositories.RoleRepository;
import account.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private RoleRepository roleRepository;
    private Logger logger;

    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           RoleRepository roleRepository,
                           Logger logger) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
        this.logger = logger;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userMapper.toDtos(userRepository.findAll());
    }

    @Transactional
    @Override
    public String deleteUser(String email) {
        if (!userRepository.existsByEmailIgnoreCase(email)) {
            throw new UserNotFoundException();
        }
        //TODO: implement JOIN query
        User user = userRepository.findByEmailIgnoreCase(email);
        if (user.getRoles().contains(roleRepository.findByCode("ROLE_ADMINISTRATOR"))) {
            throw new InvalidUserDeletionRequestException("Can't remove ADMINISTRATOR role!");
        }
        userRepository.deleteByEmail(email);
        logger.log(Event.DELETE_USER, email.toLowerCase());
        return email;
    }

    @Override
    public UserDTO grantRole(String email, String role) {
        User user = userRepository.findByEmailIgnoreCase(email);
        if (Objects.isNull(user)) {
            throw new UserNotFoundException();
        }
        Role foundRole = roleRepository.findByCode("ROLE_" + role);
        if (Objects.isNull(foundRole)) {
            throw new RoleNotFoundException();
        }
        if (foundRole.isAdministrative()
                && user.getRoles().stream().anyMatch(e -> !e.isAdministrative())
                || !foundRole.isAdministrative()
                && user.getRoles().stream().anyMatch(Role::isAdministrative)) {
            throw new InvalidCombinationOfRolesException();
        }
        user.addRole(foundRole);
        User updatedUser = userRepository.save(user);
        logger.log(Event.GRANT_ROLE, "Grant role " + role + " to " + updatedUser.getEmail());
        return userMapper.toDto(updatedUser);
    }

    @Override
    public UserDTO removeRole(String email, String role) {
        User user = userRepository.findByEmailIgnoreCase(email);
        if (Objects.isNull(user)) {
            throw new UserNotFoundException();
        }
        Role foundRole = roleRepository.findByCode("ROLE_" + role);
        if (Objects.isNull(foundRole)) {
            throw new RoleNotFoundException();
        }
        if (foundRole.isAdministrative()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't remove ADMINISTRATOR role!");
        }
        if (!user.getRoles().contains(foundRole)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user does not have a role!");
        }
        if (user.getRoles().size() < 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user must have at least one role!");
        }
        user.removeRole(foundRole);
        User updatedUser = userRepository.save(user);
        logger.log(Event.REMOVE_ROLE, "Remove role " + role + " from " + updatedUser.getEmail());
        return userMapper.toDto(updatedUser);
    }

    @Override
    public String lockUser(String email) {
        User user = userRepository.findByEmailIgnoreCase(email);
        if (Objects.isNull(user)) {
            throw new UserNotFoundException();
        }
        if (user.getRoles().stream().anyMatch(Role::isAdministrative)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't lock the ADMINISTRATOR!");
        }
        user.setAccountNonLocked(false);
        userRepository.save(user);
        logger.log(Event.LOCK_USER, "Lock user " + user.getEmail());
        return user.getEmail();
    }

    @Override
    public String unlockUser(String email) {
        User user = userRepository.findByEmailIgnoreCase(email);
        if (Objects.isNull(user)) {
            throw new UserNotFoundException();
        }
        user.setAccountNonLocked(true);
        user.setFailedAttempts(0);
        userRepository.save(user);
        logger.log(Event.UNLOCK_USER, "Unlock user " + user.getEmail());
        return user.getEmail();
    }
}

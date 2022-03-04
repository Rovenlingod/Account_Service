package account.services;

import account.domain.Role;
import account.domain.User;
import account.dtos.NewPasswordDTO;
import account.dtos.UserCreationDTO;
import account.dtos.UserDTO;
import account.exceptions.SamePasswordException;
import account.exceptions.UserExistsException;
import account.logging.Event;
import account.logging.Logger;
import account.mappers.UserMapper;
import account.repositories.RoleRepository;
import account.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private Logger logger;

    public AuthServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           PasswordEncoder passwordEncoder,
                           RoleRepository roleRepository,
                           Logger logger) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.logger = logger;
    }

    @Override
    public UserDTO signUp(UserCreationDTO user) {
        User newUser = userMapper.toEntity(user);
        if (userRepository.existsByEmail(user.getEmail().toLowerCase())) {
            throw new UserExistsException();
        }
        newUser.addRole(userRepository.count() == 0 ?
                roleRepository.findByCode("ROLE_ADMINISTRATOR") :
                roleRepository.findByCode("ROLE_USER"));
        User createdUser = userRepository.save(newUser);
        logger.log(Event.CREATE_USER, createdUser.getEmail());
        return userMapper.toDto(createdUser);
    }

    @Override
    public UserDTO changePass(UserDetails userDetails, NewPasswordDTO newPassword) {
        User currentUser = userRepository.findByEmail(userDetails.getUsername());
        if (passwordEncoder.matches(newPassword.getNewPassword(), currentUser.getPassword())) {
            throw new SamePasswordException();
        }
        currentUser.setPassword(passwordEncoder.encode(newPassword.getNewPassword()));
        User updatedUser = userRepository.save(currentUser);
        logger.log(Event.CHANGE_PASSWORD,  updatedUser.getEmail());
        return userMapper.toDto(updatedUser);
    }
}

package account.mappers;

import account.domain.Role;
import account.domain.User;
import account.dtos.UserCreationDTO;
import account.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO toDto(User user) {
        UserDTO result = new UserDTO();
        result.setEmail(user.getEmail());
        result.setLastname(user.getLastname());
        result.setName(user.getName());
        result.setId(user.getId());
        result.setRoles(user.getRoles().stream().map(Role::getCode).sorted().collect(Collectors.toList()));
        return result;
    }

    public List<UserDTO> toDtos(List<User> users) {
        return users.stream().map(this::toDto).collect(Collectors.toList());
    }

    public User toEntity(UserCreationDTO userCreationDTO) {
        User user = new User();
        user.setEmail(userCreationDTO.getEmail().toLowerCase());
        user.setLastname(userCreationDTO.getLastname());
        user.setName(userCreationDTO.getName());
        user.setAccountNonLocked(true);
        user.setPassword(passwordEncoder.encode(userCreationDTO.getPassword()));
        return user;
    }
}

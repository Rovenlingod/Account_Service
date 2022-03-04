package account.services;

import account.domain.User;
import account.dtos.NewPasswordDTO;
import account.dtos.UserCreationDTO;
import account.dtos.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {
    UserDTO signUp(UserCreationDTO user);
    UserDTO changePass(UserDetails userDetails, NewPasswordDTO newPassword);
}

package account.services;

import account.dtos.UpdateRoleDTO;
import account.dtos.UserDTO;

import java.util.List;
import java.util.Set;

public interface UserService {
    List<UserDTO> getAllUsers();
    String deleteUser(String email);
    UserDTO grantRole(String email, String role);
    UserDTO removeRole(String email, String role);
    String lockUser(String email);
    String unlockUser(String email);
}

package account.controllers;

import account.domain.AdminOperation;
import account.domain.LockOperation;
import account.dtos.SuccessMessage;
import account.dtos.UpdateRoleDTO;
import account.dtos.UpdateUserLockStatusDTO;
import account.dtos.UserDTO;
import account.exceptions.InvalidUserDeletionRequestException;
import account.exceptions.UnsupportedAdminOperationException;
import account.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @Secured("ROLE_ADMINISTRATOR")
    @GetMapping("/user")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @Secured("ROLE_ADMINISTRATOR")
    @DeleteMapping({"/user", "/user/{email}"})
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable(required = false) String email) {
        String deletedEmail = userService.deleteUser(email);
        Map<String, String> response = Map.of("user", deletedEmail,
                "status", "Deleted successfully!");
        return ResponseEntity.ok().body(response);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @PutMapping("/user/role")
    public ResponseEntity<UserDTO> updateUserRole(@RequestBody(required = false) UpdateRoleDTO roleDTO) {
        UserDTO response;
        if (roleDTO.getOperation().equals(AdminOperation.GRANT)) {
            response = userService.grantRole(roleDTO.getUser(), roleDTO.getRole());
        } else if (roleDTO.getOperation().equals(AdminOperation.REMOVE)) {
            response = userService.removeRole(roleDTO.getUser(), roleDTO.getRole());
        } else {
            throw new UnsupportedAdminOperationException();
        }
        return ResponseEntity.ok().body(response);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @PutMapping("/user/access")
    public ResponseEntity<SuccessMessage> updateUserLockStatus(@RequestBody(required = false) UpdateUserLockStatusDTO lockStatusDTO) {
        SuccessMessage response = new SuccessMessage();
        if (lockStatusDTO.getOperation().equals(LockOperation.LOCK)) {
            response.setStatus("User " + userService.lockUser(lockStatusDTO.getUser()) + " locked!");
        } else if (lockStatusDTO.getOperation().equals(LockOperation.UNLOCK)) {
            response.setStatus("User " + userService.unlockUser(lockStatusDTO.getUser()) + " unlocked!");
        } else {
            throw new UnsupportedAdminOperationException();
        }
        return ResponseEntity.ok().body(response);
    }
}

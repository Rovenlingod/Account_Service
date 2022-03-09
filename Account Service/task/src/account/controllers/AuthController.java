package account.controllers;

import account.domain.User;
import account.dtos.NewPasswordDTO;
import account.dtos.UserCreationDTO;
import account.dtos.UserDTO;
import account.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody @Valid UserCreationDTO user) {
        return ResponseEntity.ok().body(authService.signUp(user));
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_ACCOUNTANT", "ROLE_USER"})
    @PostMapping("/changepass")
    public ResponseEntity<Map<String, String>> changePass(@AuthenticationPrincipal UserDetails userDetails,
                                                          @RequestBody @Valid NewPasswordDTO newPassword) {
        UserDTO updatedUser = authService.changePass(userDetails, newPassword);
        Map<String, String> response = Map.of("email", updatedUser.getEmail(),
                "status", "The password has been updated successfully");
        return ResponseEntity.ok().body(response);
    }
}

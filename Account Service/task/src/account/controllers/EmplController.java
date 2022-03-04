package account.controllers;

import account.dtos.PaymentInfoDTO;
import account.dtos.UserCreationDTO;
import account.dtos.UserDTO;
import account.services.EmplService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("/api/empl")
@Validated
public class EmplController {

    private EmplService emplService;

    @Autowired
    public EmplController(EmplService emplService) {
        this.emplService = emplService;
    }

    @Secured({"ROLE_ACCOUNTANT", "ROLE_USER"})
    @GetMapping("/payment")
    public ResponseEntity<List<PaymentInfoDTO>> payment(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok().body(emplService.paymentForCurrentUser(userDetails));
    }

    @Secured({"ROLE_ACCOUNTANT", "ROLE_USER"})
    @GetMapping(value = "/payment", params = "period")
    public ResponseEntity<PaymentInfoDTO> payment(@AuthenticationPrincipal UserDetails userDetails,
                                                  @RequestParam @Pattern(regexp = "^(1[0-2]|0[1-9])-[0-9]{4}$") String period) {
        return ResponseEntity.ok().body(emplService.paymentForCurrentUser(userDetails, period));
    }
}

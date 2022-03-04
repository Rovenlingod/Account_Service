package account.controllers;

import account.dtos.LogEntryDTO;
import account.services.SecurityService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/security")
public class SecurityController {

    private SecurityService securityService;

    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Secured("ROLE_AUDITOR")
    @GetMapping("/events")
    public ResponseEntity<List<LogEntryDTO>> getAllEvents() {
        return ResponseEntity.ok().body(securityService.getAllEvents());
    }
}

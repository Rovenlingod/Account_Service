package account.controllers;

import account.dtos.PaymentCreationDTO;
import account.dtos.SuccessMessage;
import account.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/acct")
public class PaymentController {

    private PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Secured("ROLE_ACCOUNTANT")
    @PostMapping("/payments")
    public ResponseEntity<SuccessMessage> createPayments(@RequestBody(required = false) List<@Valid PaymentCreationDTO> paymentCreationDTOs) {
        paymentService.createPayments(paymentCreationDTOs);
        return ResponseEntity.ok().body(new SuccessMessage("Added successfully!"));
    }

    @Secured("ROLE_ACCOUNTANT")
    @PutMapping("/payments")
    public ResponseEntity<SuccessMessage> updatePayment(@RequestBody(required = false) @Valid PaymentCreationDTO paymentCreationDTO) {
        paymentService.updatePayment(paymentCreationDTO);
        return ResponseEntity.ok().body(new SuccessMessage("Updated successfully!"));
    }
}

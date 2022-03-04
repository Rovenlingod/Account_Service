package account.services;

import account.dtos.PaymentInfoDTO;
import account.dtos.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface EmplService {
    List<PaymentInfoDTO> paymentForCurrentUser(UserDetails userDetails);
    PaymentInfoDTO paymentForCurrentUser(UserDetails userDetails, String period);

}

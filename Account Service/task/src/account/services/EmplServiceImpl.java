package account.services;

import account.domain.Payment;
import account.domain.User;
import account.dtos.PaymentInfoDTO;
import account.dtos.UserDTO;
import account.exceptions.InvalidDateException;
import account.exceptions.InvalidPaymentRequestException;
import account.mappers.PaymentMapper;
import account.mappers.UserMapper;
import account.repositories.PaymentRepository;
import account.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class EmplServiceImpl implements EmplService {

    private UserRepository userRepository;
    private PaymentRepository paymentRepository;
    private PaymentMapper paymentMapper;

    public EmplServiceImpl(UserRepository userRepository,
                           PaymentRepository paymentRepository,
                           PaymentMapper paymentMapper) {
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public List<PaymentInfoDTO> paymentForCurrentUser(UserDetails userDetails) {
        User currentUser = userRepository.findByEmailIgnoreCase(userDetails.getUsername());
        List<Payment> payments = paymentRepository.findByEmployeeOrderByPeriodDesc(currentUser.getEmail());
        return paymentMapper.toDtos(payments, currentUser);
    }

    @Override
    public PaymentInfoDTO paymentForCurrentUser(UserDetails userDetails, String period) {
        User currentUser = userRepository.findByEmailIgnoreCase(userDetails.getUsername());
        try {
            Payment payment = paymentRepository.findByEmployeeAndPeriod(currentUser.getEmail(),
                    new SimpleDateFormat("MM-yyyy").parse(period))
                    .orElseThrow(() -> new InvalidPaymentRequestException("No such payment"));
            return paymentMapper.toDto(payment, currentUser);
        } catch (ParseException e) {
            throw new InvalidDateException();
        }
    }
}

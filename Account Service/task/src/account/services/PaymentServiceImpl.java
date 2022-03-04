package account.services;

import account.domain.Payment;
import account.dtos.PaymentCreationDTO;
import account.exceptions.InvalidDateException;
import account.exceptions.InvalidPaymentRequestException;
import account.exceptions.NoSuchUserException;
import account.mappers.PaymentMapper;
import account.repositories.PaymentRepository;
import account.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private UserRepository userRepository;
    private PaymentRepository paymentRepository;
    private PaymentMapper paymentMapper;

    public PaymentServiceImpl(UserRepository userRepository, PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    @Override
    @Transactional
    public void createPayments(List<PaymentCreationDTO> paymentCreationDTOs) {
        for (PaymentCreationDTO paymentCreationDTO :
             paymentCreationDTOs) {
            try {
                Payment payment = paymentMapper.toEntity(paymentCreationDTO);
                if (!userRepository.existsByEmail(payment.getEmployee())) {
                    throw new NoSuchUserException();
                }
                if (paymentRepository.existsByEmployeeAndPeriod(payment.getEmployee(), payment.getPeriod())) {
                    throw new InvalidPaymentRequestException("Employee & date pair is not unique");
                }
                paymentRepository.save(payment);
            } catch (ParseException e) {
                throw new InvalidDateException();
            }
        }
    }

    @Override
    public void updatePayment(PaymentCreationDTO paymentCreationDTO) {
        try {
            Payment payment = paymentMapper.toEntity(paymentCreationDTO);
            if (!userRepository.existsByEmail(payment.getEmployee())) {
                throw new NoSuchUserException();
            }
            paymentRepository.save(payment);
        } catch (ParseException e) {
            throw new InvalidDateException();
        }

    }
}

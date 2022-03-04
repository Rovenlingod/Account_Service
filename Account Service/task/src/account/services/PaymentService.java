package account.services;

import account.dtos.PaymentCreationDTO;

import java.util.List;

public interface PaymentService {
    void createPayments(List<PaymentCreationDTO> paymentCreationDTOs);
    void updatePayment(PaymentCreationDTO paymentCreationDTO);
}

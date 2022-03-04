package account.repositories;

import account.domain.Payment;
import account.domain.PaymentId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, PaymentId> {
    boolean existsByEmployeeAndPeriod(String employee, Date period);
    Optional<Payment> findByEmployeeAndPeriod(String employee, Date period);
    List<Payment> findByEmployeeOrderByPeriodDesc(String employee);
}

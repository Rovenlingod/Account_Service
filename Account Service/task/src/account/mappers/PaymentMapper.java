package account.mappers;

import account.domain.Payment;
import account.domain.User;
import account.dtos.PaymentCreationDTO;
import account.dtos.PaymentInfoDTO;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Formatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentMapper {

    public Payment toEntity(PaymentCreationDTO paymentCreationDTO) throws ParseException {
        Payment result = new Payment();
        result.setEmployee(paymentCreationDTO.getEmployee());
        result.setSalary(paymentCreationDTO.getSalary());
        result.setPeriod(new SimpleDateFormat("MM-yyyy").parse(paymentCreationDTO.getPeriod()));
        return result;
    }

    public PaymentInfoDTO toDto(Payment payment, User currentUser) {
        PaymentInfoDTO result = new PaymentInfoDTO();
        result.setName(currentUser.getName());
        result.setLastName(currentUser.getLastname());
        result.setSalary(payment.getSalary() / 100 + " dollar(s) " + payment.getSalary() % 100 + " cent(s)");
        LocalDate localDate = LocalDate.parse(payment.getPeriod().toString());
        result.setPeriod(StringUtils.capitalize(localDate.getMonth().toString().toLowerCase()) + "-" + localDate.getYear());
        return result;
    }

    public List<PaymentInfoDTO> toDtos(List<Payment> payments, User currentUser) {
        return payments.stream().map(e -> toDto(e, currentUser)).collect(Collectors.toList());
    }
}

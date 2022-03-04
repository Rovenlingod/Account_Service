package account.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class PaymentId implements Serializable {

    private String employee;
    private Date period;

    public PaymentId() {
    }

    public PaymentId(String employee, Date period) {
        this.employee = employee;
        this.period = period;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public Date getPeriod() {
        return period;
    }

    public void setPeriod(Date period) {
        this.period = period;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentId paymentId = (PaymentId) o;
        return employee.equals(paymentId.employee) && period.equals(paymentId.period);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, period);
    }
}

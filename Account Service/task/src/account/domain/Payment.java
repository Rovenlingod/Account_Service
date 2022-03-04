package account.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@IdClass(PaymentId.class)
public class Payment {

    @Id
    @Column
    private String employee;
    @Id
    @Temporal(TemporalType.DATE)
    @Column
    private Date period;
    @Column
    private Long salary;

    public Payment() {
    }

    public Payment(String employee, Date period, Long salary) {
        this.employee = employee;
        this.period = period;
        this.salary = salary;
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

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }
}

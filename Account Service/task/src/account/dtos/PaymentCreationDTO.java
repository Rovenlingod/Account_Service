package account.dtos;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class PaymentCreationDTO {

    @NotBlank
    @NotNull
    private String employee;
    @NotBlank
    @NotNull
    @Pattern(regexp = "^(1[0-2]|0[1-9])-[0-9]{4}$")
    private String period;
    @NotNull
    @Min(0)
    private Long salary;

    public PaymentCreationDTO() {
    }

    public PaymentCreationDTO(String employee, String period, Long salary) {
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

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }
}

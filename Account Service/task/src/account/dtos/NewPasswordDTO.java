package account.dtos;

import account.annotations.BreachedValidation;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NewPasswordDTO {

    @JsonProperty("new_password")
    @NotBlank
    @NotNull
    @Size(min = 12, message = "Password length must be 12 chars minimum!")
    @BreachedValidation
    private String newPassword;

    public NewPasswordDTO() {
    }

    public NewPasswordDTO(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}

package account.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class BreachedValidator implements ConstraintValidator<BreachedValidation, String> {
    public boolean isValid(String password, ConstraintValidatorContext cxt) {
        if (password == null) {
            return false;
        }
        List<String> list = List.of("PasswordForJanuary", "PasswordForFebruary", "PasswordForMarch", "PasswordForApril",
                "PasswordForMay", "PasswordForJune", "PasswordForJuly", "PasswordForAugust",
                "PasswordForSeptember", "PasswordForOctober", "PasswordForNovember", "PasswordForDecember");
        return !list.contains(password);
    }
}

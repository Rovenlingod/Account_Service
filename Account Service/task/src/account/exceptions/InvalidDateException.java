package account.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid pattern used in provided date. It should be 'MM-yyyy'")
public class InvalidDateException extends RuntimeException {
}

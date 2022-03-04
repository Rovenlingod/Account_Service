package account.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidUserDeletionRequestException extends RuntimeException {

    public InvalidUserDeletionRequestException() {
    }

    public InvalidUserDeletionRequestException(String message) {
        super(message);
    }
}

package account.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidPaymentRequestException extends RuntimeException {

    public InvalidPaymentRequestException() {
    }

    public InvalidPaymentRequestException(String message) {
        super(message);
    }
}

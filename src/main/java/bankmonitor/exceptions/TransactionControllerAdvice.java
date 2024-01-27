package bankmonitor.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class TransactionControllerAdvice {

    @ExceptionHandler(TransactionNotFoundException.class)
    @ResponseStatus(BAD_REQUEST)
    ApiError handleVoucherNotFoundException(TransactionNotFoundException exception) {
        return new ApiError(exception.getMessage());
    }
}

record ApiError(String message) {}

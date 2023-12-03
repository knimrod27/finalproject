package fasttrack.it.Hangman.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HangmanControllerAdvice {
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)

    public ApiError handleResourceNotFound(ResourceNotFoundException exception) {
        return ApiError.builder()
                .message(exception.getMessage())
                .entityId(exception.getResourceId())
                .internalErrorCode("Err-01")
                .build();
    }

}

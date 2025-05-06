package nsu.fit.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {

    private ErrorType errorType;

    public ServiceException(String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }
}

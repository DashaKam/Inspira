package nsu.fit.web.controller;

import lombok.extern.slf4j.Slf4j;
import nsu.fit.exception.AuthException;
import nsu.fit.exception.ServiceException;
import nsu.fit.web.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ResponseExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponseDto> handleServiceException(ServiceException e) {
        log.error("Request failed. Got ServiceException", e);

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                        .body(ErrorResponseDto.builder()
                                .message(e.getMessage())
                                .errorType(e.getErrorType())
                                .build());
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponseDto> handleAuthException(AuthException e) {
        log.error("Запрос завершен с ошибкой: " +
                "пользователь не имеет достаточных прав или не авторизован в системе", e);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponseDto.builder()
                        .message(e.getMessage())
                        .build());
    }

}

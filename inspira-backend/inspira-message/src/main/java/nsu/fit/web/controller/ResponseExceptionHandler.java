package nsu.fit.web.controller;

import lombok.extern.slf4j.Slf4j;
import nsu.fit.exception.AuthException;
import nsu.fit.exception.ServiceException;
import nsu.fit.web.dto.ErrorResponseDto;
import nsu.fit.web.dto.FieldValidationErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.Optional;

@ControllerAdvice
@Slf4j
public class ResponseExceptionHandler {

    private static final String INVALID_REQUEST_PARAMETERS = "INVALID_REQUEST_PARAMETERS";

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponseDto> handleServiceException(ServiceException e) {
        log.error("Запрос завершен с ошибкой: ServiceException", e);
        String sErrorType = Optional.ofNullable(e.getErrorType())
                .map(Enum::name)
                .orElse(null);

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                        .body(ErrorResponseDto.builder()
                                .message(e.getMessage())
                                .errorType(sErrorType)
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

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            WebRequest request
    ) {
        String requestUrl = ((ServletWebRequest) request).getRequest().getRequestURI();
        String errorMessage = "Ошибка при обработке запроса! URI запроса: %s".formatted(requestUrl);
        log.error(errorMessage, ex);

        ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                .errorType(INVALID_REQUEST_PARAMETERS)
                .message("Неверные параметры запроса")
                .details(ex.getFieldErrors().stream()
                        .map(fieldError -> FieldValidationErrorDto.builder()
                                .path(fieldError.getField())
                                .constraint(fieldError.getCode())
                                .message(fieldError.getDefaultMessage())
                                .build())
                        .toList())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}

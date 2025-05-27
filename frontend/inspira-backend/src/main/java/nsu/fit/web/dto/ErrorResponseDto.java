package nsu.fit.web.dto;

import lombok.Builder;
import lombok.Data;
import nsu.fit.exception.ErrorType;

@Data
@Builder
public class ErrorResponseDto {

    private String message;

    private ErrorType errorType;

    private Object details;

}

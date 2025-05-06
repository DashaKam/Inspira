package nsu.fit.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDto {

    private String message;

    private String errorType;

    private Object details;

}

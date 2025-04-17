package nsu.fit.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangeMessageTypeRequestDto {
    @NotNull
    private MessageTypeDto messageType;
}

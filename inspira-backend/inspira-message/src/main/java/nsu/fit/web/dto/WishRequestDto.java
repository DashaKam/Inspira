package nsu.fit.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WishRequestDto {
    private String receiverNickname;
    @NotBlank
    private String message;
    @NotBlank
    private Boolean anonymous;
}

package nsu.fit.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistrationRequestDto {

    @NotBlank
    private String nickname;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

}

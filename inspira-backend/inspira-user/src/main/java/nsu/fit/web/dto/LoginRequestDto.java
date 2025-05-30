package nsu.fit.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequestDto {

    @NotBlank
    @Size(min = 4, max = 20)
    @Pattern(regexp = "^(?=.*?[A-Za-z]{4})[A-Za-z\\d_-]+$")
    private String nickname;

    @NotBlank
    @Size(min = 8)
    @Pattern(regexp = "^(?=.*?[A-Za-z]{4})(?=.*\\d)[A-Za-z\\d@$!%*?&_-]+$")
    private String password;

}

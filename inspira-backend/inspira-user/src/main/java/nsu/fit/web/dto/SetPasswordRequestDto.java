package nsu.fit.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SetPasswordRequestDto {

    @NotBlank
    @Size(min = 8)
    @Pattern(regexp = "^(?=.*?[A-Za-z]{4})(?=.*\\d)[A-Za-z\\d@$!%*?&_-]+$")
    private String password;

}

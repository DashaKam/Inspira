package nsu.fit.web.dto;

import lombok.Data;

@Data
public class UserDto {
    private Integer id;

    private String nickname;

    private String name;

    private String password;
}

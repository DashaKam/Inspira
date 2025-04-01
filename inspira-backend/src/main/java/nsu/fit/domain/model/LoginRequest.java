package nsu.fit.domain.model;

import lombok.Data;

@Data
public class LoginRequest {

    private String nickname;

    private String password;

}

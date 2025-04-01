package nsu.fit.domain.model;

import lombok.Data;

@Data
public class RegistrationRequest {

    private String nickname;

    private String name;

    private String password;
}

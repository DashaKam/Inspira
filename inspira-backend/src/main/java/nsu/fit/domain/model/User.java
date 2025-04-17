package nsu.fit.domain.model;

import lombok.Builder;
import lombok.Data;

import java.security.Principal;

@Data
@Builder
public class User implements Principal {

    private Integer id;

    private String nickname;

    private String name;

    private String password;

}

package nsu.fit.domain.model;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class UserSearchFilter {

    private String userNickname;

    private Integer userId;

}

package nsu.fit.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UiFriendRequest {

    private String userNickname;

}

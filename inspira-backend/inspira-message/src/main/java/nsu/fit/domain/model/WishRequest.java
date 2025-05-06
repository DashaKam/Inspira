package nsu.fit.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WishRequest {
    private String receiverNickname;
    private String message;
    private Boolean anonymous;
}

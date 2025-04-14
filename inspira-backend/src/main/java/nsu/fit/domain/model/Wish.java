package nsu.fit.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Wish {

    private Integer id;

    private Integer senderId;

    private Integer receiverId;

    private String message;
}

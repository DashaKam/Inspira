package nsu.fit.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Friendship {

    private Integer id;

    private Integer fstUserId;

    private Integer sndUserId;

}

package nsu.fit.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Settings {

    private Integer userId;

    private MessageType messageType;
}

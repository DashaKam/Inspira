package nsu.fit.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {

    private String messageText;

    private String senderNickname;

    private MessageType messageType;

}

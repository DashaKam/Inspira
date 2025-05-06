package nsu.fit.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nsu.fit.messaging.user.dto.MessageTypeDto;

@Data
@AllArgsConstructor
public class MessageResponseDto {

    private String messageText;

    private String senderNickname;

    private MessageTypeDto messageType;

}

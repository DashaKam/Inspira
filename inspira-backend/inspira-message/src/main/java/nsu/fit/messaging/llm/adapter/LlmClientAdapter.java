package nsu.fit.messaging.llm.adapter;

import lombok.RequiredArgsConstructor;
import nsu.fit.domain.model.GeneratedMessage;
import nsu.fit.domain.model.MessageType;
import nsu.fit.domain.model.ModerateWishRequest;
import nsu.fit.domain.model.ModerateWishResponse;
import nsu.fit.domain.port.LlmClientPort;
import nsu.fit.messaging.llm.client.LlmClient;
import nsu.fit.messaging.llm.dto.GeneratedMessageDto;
import nsu.fit.messaging.llm.dto.MessageTypeDto;
import nsu.fit.messaging.llm.dto.ModerateWishRequestDto;
import nsu.fit.messaging.llm.dto.ModerateWishResponseDto;
import nsu.fit.messaging.llm.mapper.LlmClientMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LlmClientAdapter implements LlmClientPort {

    private final LlmClient llmClient;
    private final LlmClientMapper llmClientMapper;

    @Override
    public ModerateWishResponse moderateWish(ModerateWishRequest moderateWishRequest) {
        ModerateWishRequestDto moderateWishRequestDto = llmClientMapper.moderateWishRequestToDto(moderateWishRequest);

        ModerateWishResponseDto moderateWishResponseDto = llmClient.moderateWish(moderateWishRequestDto);
        return llmClientMapper.dtoToModerateWishResponse(moderateWishResponseDto);
    }

    @Override
    public GeneratedMessage generateMessage(MessageType messageType) {
        MessageTypeDto messageTypeDto = llmClientMapper.messageTypeToDto(messageType);

        GeneratedMessageDto generatedMessageDto = llmClient.generateMessage(messageTypeDto);
        return llmClientMapper.dtoToGeneratedMessage(generatedMessageDto);
    }
}

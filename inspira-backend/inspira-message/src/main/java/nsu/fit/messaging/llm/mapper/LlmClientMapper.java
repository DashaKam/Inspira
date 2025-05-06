package nsu.fit.messaging.llm.mapper;

import nsu.fit.domain.model.GeneratedMessage;
import nsu.fit.domain.model.MessageType;
import nsu.fit.domain.model.ModerateWishRequest;
import nsu.fit.domain.model.ModerateWishResponse;
import nsu.fit.messaging.llm.dto.GeneratedMessageDto;
import nsu.fit.messaging.llm.dto.MessageTypeDto;
import nsu.fit.messaging.llm.dto.ModerateWishRequestDto;
import nsu.fit.messaging.llm.dto.ModerateWishResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface LlmClientMapper {
    GeneratedMessage dtoToGeneratedMessage(GeneratedMessageDto generatedMessageDto);
    ModerateWishRequestDto moderateWishRequestToDto(ModerateWishRequest moderateWishRequest);
    MessageTypeDto messageTypeToDto(MessageType messageType);
    ModerateWishResponse dtoToModerateWishResponse(ModerateWishResponseDto moderateWishResponseDto);
}

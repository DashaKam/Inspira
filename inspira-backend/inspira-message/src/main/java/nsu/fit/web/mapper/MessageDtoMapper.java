package nsu.fit.web.mapper;

import nsu.fit.domain.model.Message;
import nsu.fit.web.dto.MessageResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface MessageDtoMapper
{
    Message dtoToMessage(MessageResponseDto messageResponseDto);

    MessageResponseDto messageToDto(Message message);
}

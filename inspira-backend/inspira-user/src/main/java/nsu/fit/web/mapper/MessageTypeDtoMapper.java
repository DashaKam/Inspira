package nsu.fit.web.mapper;

import nsu.fit.domain.model.MessageType;
import nsu.fit.web.dto.MessageTypeDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface MessageTypeDtoMapper {

    MessageType dtoToMessageType(MessageTypeDto messageTypeDto);

    MessageTypeDto messageTypeToDto(MessageType messageType);

}

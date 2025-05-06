package nsu.fit.messaging.user.mapper;

import nsu.fit.domain.model.MessageType;
import nsu.fit.domain.model.User;
import nsu.fit.domain.model.UserSearchFilter;
import nsu.fit.messaging.user.dto.MessageTypeDto;
import nsu.fit.messaging.user.dto.UserDto;
import nsu.fit.messaging.user.dto.UserSearchFilterDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserClientMapper {
    MessageType dtoToMessageType(MessageTypeDto messageTypeDto);
    User dtoToUser(UserDto userDto);
    UserSearchFilterDto userSearchFilterToDto(UserSearchFilter userSearchFilter);
}

package nsu.fit.web.mapper;

import nsu.fit.domain.model.User;
import nsu.fit.web.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserDtoMapper {

    UserDto userToDto(User user);

}

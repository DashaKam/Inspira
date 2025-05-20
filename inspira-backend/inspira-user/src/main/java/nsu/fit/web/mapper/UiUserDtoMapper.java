package nsu.fit.web.mapper;

import nsu.fit.domain.model.UiUser;
import nsu.fit.web.dto.UiUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UiUserDtoMapper {

    List<UiUserDto> friendListToFriendDtoList(List<UiUser> uiUserList);

}

package nsu.fit.web.mapper;

import nsu.fit.domain.model.Friend;
import nsu.fit.web.dto.FriendDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface FriendDtoMapper {

    List<FriendDto> friendListToFriendDtoList(List<Friend> friendList);

}

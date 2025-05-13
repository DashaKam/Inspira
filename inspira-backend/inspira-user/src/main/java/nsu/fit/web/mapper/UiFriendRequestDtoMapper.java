package nsu.fit.web.mapper;

import nsu.fit.domain.model.UiFriendRequest;
import nsu.fit.web.dto.UiFriendRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UiFriendRequestDtoMapper {

    UiFriendRequest dtoToUiFriendRequest(UiFriendRequestDto uiFriendRequestDto);

    List<UiFriendRequestDto> uiFriendRequestListToDtoList(List<UiFriendRequest> uiFriendRequestList);

}

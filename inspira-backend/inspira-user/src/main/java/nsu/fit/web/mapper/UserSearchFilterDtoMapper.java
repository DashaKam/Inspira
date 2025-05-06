package nsu.fit.web.mapper;

import nsu.fit.domain.model.UserSearchFilter;
import nsu.fit.web.dto.UserSearchFilterDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserSearchFilterDtoMapper {

    UserSearchFilter dtoToUserSearchFilter(UserSearchFilterDto userSearchFilterDto);

}

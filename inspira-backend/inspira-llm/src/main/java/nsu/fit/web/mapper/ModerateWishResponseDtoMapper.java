package nsu.fit.web.mapper;

import nsu.fit.domain.model.ModerateWishResponse;
import nsu.fit.web.dto.ModerateWishResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ModerateWishResponseDtoMapper {
    ModerateWishResponseDto moderateWishResponseToDto(ModerateWishResponse moderateWishResponse);
}

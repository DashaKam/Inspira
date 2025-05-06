package nsu.fit.web.mapper;

import nsu.fit.domain.model.ModerateWishResponse;
import nsu.fit.web.dto.WishResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface WishResponseDtoMapper {
    WishResponseDto moderateWishResponseToDto(ModerateWishResponse moderateWishResponse);
}

package nsu.fit.web.mapper;

import nsu.fit.domain.model.ModerateWishRequest;
import nsu.fit.web.dto.ModerateWishRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ModerateWishRequestDtoMapper {

    ModerateWishRequest dtoToModerateWishRequest(ModerateWishRequestDto moderateWishRequestDto);

    ModerateWishRequestDto moderateWishRequestToDto(ModerateWishRequest moderateWishRequest);
}

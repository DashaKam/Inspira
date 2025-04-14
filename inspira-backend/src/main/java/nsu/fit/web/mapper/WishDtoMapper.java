package nsu.fit.web.mapper;

import nsu.fit.domain.model.WishRequest;
import nsu.fit.web.dto.WishRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface WishDtoMapper {
    WishRequest dtoToWishRequest(WishRequestDto wishRequestDto);
}

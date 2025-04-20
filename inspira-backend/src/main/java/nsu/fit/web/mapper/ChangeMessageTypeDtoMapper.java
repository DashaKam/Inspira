package nsu.fit.web.mapper;

import nsu.fit.domain.model.ChangeMessageTypeRequest;
import nsu.fit.web.dto.ChangeMessageTypeRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ChangeMessageTypeDtoMapper {

    ChangeMessageTypeRequest dtoToChangeMessageTypeRequest(ChangeMessageTypeRequestDto changeMessageTypeRequestDto);

    ChangeMessageTypeRequestDto changeMessageTypeRequestToDto(ChangeMessageTypeRequest changeMessageTypeRequest);
}

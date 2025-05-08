package nsu.fit.web.mapper;

import nsu.fit.domain.model.SetPasswordRequest;
import nsu.fit.web.dto.SetPasswordRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface SetPasswordRequestDtoMapper {

    SetPasswordRequest dtoToSetPasswordRequest(SetPasswordRequestDto setPasswordRequestDto);

}

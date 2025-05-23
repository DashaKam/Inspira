package nsu.fit.web.mapper;

import nsu.fit.domain.model.SetNicknameRequest;
import nsu.fit.web.dto.SetNicknameRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface SetNicknameRequestDtoMapper {

    SetNicknameRequest dtoToSetNicknameRequest(SetNicknameRequestDto setNicknameRequestDto);

}

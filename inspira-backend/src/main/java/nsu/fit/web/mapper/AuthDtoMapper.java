package nsu.fit.web.mapper;

import nsu.fit.domain.model.LoginRequest;
import nsu.fit.domain.model.RegistrationRequest;
import nsu.fit.web.dto.LoginRequestDto;
import nsu.fit.web.dto.RegistrationRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AuthDtoMapper {

    LoginRequest dtoToLoginRequest(LoginRequestDto loginRequestDto);

    RegistrationRequest dtoToRegistrationRequest(RegistrationRequestDto loginRequestDto);

}

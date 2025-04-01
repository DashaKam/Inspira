package nsu.fit.web.mapper;

import javax.annotation.processing.Generated;
import nsu.fit.domain.model.LoginRequest;
import nsu.fit.domain.model.RegistrationRequest;
import nsu.fit.web.dto.LoginRequestDto;
import nsu.fit.web.dto.RegistrationRequestDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-01T21:53:39+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 20.0.2.1 (Amazon.com Inc.)"
)
@Component
public class AuthDtoMapperImpl implements AuthDtoMapper {

    @Override
    public LoginRequest dtoToLoginRequest(LoginRequestDto loginRequestDto) {
        if ( loginRequestDto == null ) {
            return null;
        }

        LoginRequest loginRequest = new LoginRequest();

        loginRequest.setNickname( loginRequestDto.getNickname() );
        loginRequest.setPassword( loginRequestDto.getPassword() );

        return loginRequest;
    }

    @Override
    public RegistrationRequest dtoToRegistrationRequest(RegistrationRequestDto loginRequestDto) {
        if ( loginRequestDto == null ) {
            return null;
        }

        RegistrationRequest registrationRequest = new RegistrationRequest();

        registrationRequest.setNickname( loginRequestDto.getNickname() );
        registrationRequest.setName( loginRequestDto.getName() );
        registrationRequest.setPassword( loginRequestDto.getPassword() );

        return registrationRequest;
    }
}

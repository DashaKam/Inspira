package nsu.fit.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nsu.fit.db.repository.UserRepository;
import nsu.fit.domain.model.LoginRequest;
import nsu.fit.domain.model.RegistrationRequest;
import nsu.fit.domain.service.UserService;
import nsu.fit.web.dto.AuthResponseDto;
import nsu.fit.web.dto.LoginRequestDto;
import nsu.fit.web.dto.RegistrationRequestDto;
import nsu.fit.web.mapper.AuthDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
    private final UserService userService;
    private final AuthDtoMapper authDtoMapper;

    @PostMapping("/register")
    public AuthResponseDto register(@RequestBody @Valid RegistrationRequestDto registrationRequestDto) {
        RegistrationRequest registrationRequest = authDtoMapper.dtoToRegistrationRequest(registrationRequestDto);
        String token = userService.registerUser(registrationRequest);
        return new AuthResponseDto(token);
    }

    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        LoginRequest loginRequest = authDtoMapper.dtoToLoginRequest(loginRequestDto);
        String token = userService.loginUser(loginRequest);
        return new AuthResponseDto(token);
    }
}

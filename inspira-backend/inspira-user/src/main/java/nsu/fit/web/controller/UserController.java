package nsu.fit.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nsu.fit.domain.model.LoginRequest;
import nsu.fit.domain.model.RegistrationRequest;
import nsu.fit.domain.model.SetNicknameRequest;
import nsu.fit.domain.model.SetPasswordRequest;
import nsu.fit.domain.model.User;
import nsu.fit.domain.service.UserService;
import nsu.fit.web.dto.AuthResponseDto;
import nsu.fit.web.dto.LoginRequestDto;
import nsu.fit.web.dto.RegistrationRequestDto;
import nsu.fit.web.dto.SetNicknameRequestDto;
import nsu.fit.web.dto.SetPasswordRequestDto;
import nsu.fit.web.dto.UserDto;
import nsu.fit.web.dto.UserSearchFilterDto;
import nsu.fit.web.mapper.AuthDtoMapper;
import nsu.fit.web.mapper.SetNicknameRequestDtoMapper;
import nsu.fit.web.mapper.SetPasswordRequestDtoMapper;
import nsu.fit.web.mapper.UserDtoMapper;
import nsu.fit.web.mapper.UserSearchFilterDtoMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    private final AuthDtoMapper authDtoMapper;
    private final UserSearchFilterDtoMapper userSearchFilterDtoMapper;
    private final UserDtoMapper userDtoMapper;
    private final SetNicknameRequestDtoMapper setNicknameRequestDtoMapper;
    private final SetPasswordRequestDtoMapper setPasswordRequestDtoMapper;

    @PostMapping("/register")
    @Operation(
            tags = "Внешние API",
            summary = "Регистрация нового пользователя"
    )
    public AuthResponseDto register(@RequestBody @Valid RegistrationRequestDto registrationRequestDto) {
        RegistrationRequest registrationRequest = authDtoMapper.dtoToRegistrationRequest(registrationRequestDto);
        String token = userService.registerUser(registrationRequest);
        return new AuthResponseDto(token);
    }

    @PostMapping("/login")
    @Operation(
            tags = "Внешние API",
            summary = "Авторизация в системе"
    )
    public AuthResponseDto login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        LoginRequest loginRequest = authDtoMapper.dtoToLoginRequest(loginRequestDto);
        String token = userService.loginUser(loginRequest);
        return new AuthResponseDto(token);
    }
    @GetMapping("/user")
    @Operation(
            tags = "Внешние API",
            summary = "Получение данных текущего пользователя"
    )
    public UserDto getUser() {
        return userDtoMapper.userToDto(userService.getUser());
    }

    @GetMapping("/search")
    @Operation(
            tags = "Внутренние API",
            summary = "Поиск пользователя в системе",
            description = "Поиск пользователя по его никнейму и / или id"
    )
    public UserDto searchUser(@ModelAttribute UserSearchFilterDto userSearchFilterDto) {
        User user = userService
                .getUserBySearchFilter(userSearchFilterDtoMapper.dtoToUserSearchFilter(userSearchFilterDto));

        return userDtoMapper.userToDto(user);
    }

    @PostMapping("/set-nickname")
    @Operation(
            tags = "Внешние API",
            summary = "Изменение никнейма пользователя"
    )
    public void setNickname(@RequestBody @Valid SetNicknameRequestDto setNicknameRequestDto) {
        SetNicknameRequest setNicknameRequest =
                setNicknameRequestDtoMapper.dtoToSetNicknameRequest(setNicknameRequestDto);
        userService.setNickname(setNicknameRequest);
    }

    @PostMapping("/set-password")
    @Operation(
            tags = "Внешние API",
            summary = "Изменение пароля пользователя"
    )
    public void setPassword(@RequestBody @Valid SetPasswordRequestDto setPasswordRequestDto) {
        SetPasswordRequest setPasswordRequest =
                setPasswordRequestDtoMapper.dtoToSetPasswordRequest(setPasswordRequestDto);
        userService.setPassword(setPasswordRequest);
    }
}

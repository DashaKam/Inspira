package nsu.fit.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nsu.fit.domain.model.LoginRequest;
import nsu.fit.domain.model.RegistrationRequest;
import nsu.fit.domain.model.User;
import nsu.fit.domain.service.UserService;
import nsu.fit.web.dto.AuthResponseDto;
import nsu.fit.web.dto.LoginRequestDto;
import nsu.fit.web.dto.RegistrationRequestDto;
import nsu.fit.web.dto.UserDto;
import nsu.fit.web.dto.UserSearchFilterDto;
import nsu.fit.web.mapper.AuthDtoMapper;
import nsu.fit.web.mapper.UserDtoMapper;
import nsu.fit.web.mapper.UserSearchFilterDtoMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/search")
    public UserDto searchUser(@ModelAttribute UserSearchFilterDto userSearchFilterDto) {
        User user = userService
                .getUserBySearchFilter(userSearchFilterDtoMapper.dtoToUserSearchFilter(userSearchFilterDto));

        return userDtoMapper.userToDto(user);
    }

}

package nsu.fit.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.fit.config.ContextProvider;
import nsu.fit.domain.model.LoginRequest;
import nsu.fit.domain.model.RegistrationRequest;
import nsu.fit.domain.model.SetNicknameRequest;
import nsu.fit.domain.model.SetPasswordRequest;
import nsu.fit.domain.model.User;
import nsu.fit.domain.model.UserSearchFilter;
import nsu.fit.domain.port.UserRepositoryPort;
import nsu.fit.exception.AuthException;
import nsu.fit.exception.ErrorType;
import nsu.fit.exception.ServiceException;
import nsu.fit.util.JwtUtil;
import org.mapstruct.control.MappingControl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepositoryPort userRepository;
    private final SettingsService settingsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ContextProvider contextProvider;

    public User getUser() {
        return getUserById(contextProvider.getUserId());
    }

    public String loginUser(LoginRequest loginRequest) {
        User user = getUserByNickname(loginRequest.getNickname());

        boolean doesPasswordMatch = bCryptPasswordEncoder.matches(
                loginRequest.getPassword(),
                user.getPassword());
        if (!doesPasswordMatch) {
            throw new AuthException("Неправильный пароль");
        }

        return JwtUtil.generateToken(String.valueOf(user.getId()));
    }

    public String registerUser(RegistrationRequest registrationRequest) {
        boolean doesUserExist = userRepository.existsByNickname(registrationRequest.getNickname());
        if (doesUserExist) {
            String errorMessage = "Пользователь с таким никнеймом уже зарегистрирован";
            log.error(errorMessage);
            throw new ServiceException(errorMessage, ErrorType.USER_NICKNAME_OCCUPIED);
        }

        User savedUser = userRepository.save(buildUser(registrationRequest));
        settingsService.createDefaultSettings(savedUser.getId());

        return JwtUtil.generateToken(String.valueOf(savedUser.getId()));
    }

    public User getUserBySearchFilter(UserSearchFilter userSearchFilter) {
        User user = userRepository.search(userSearchFilter);

        if (user == null) {
            String errorMessage = "Пользователь с никнеймом %s или id %d не найден"
                    .formatted(userSearchFilter.getUserNickname(), userSearchFilter.getUserId());
            log.error(errorMessage);
            throw new ServiceException(errorMessage, ErrorType.USER_NOT_FOUND);
        }

        return user;
    }

    public void setNickname(SetNicknameRequest setNicknameRequest) {
        User user = userRepository.findById(contextProvider.getUserId());
        user.setNickname(setNicknameRequest.getNickname());
        userRepository.save(user);
    }

    public void setPassword(SetPasswordRequest setPasswordRequest) {
        User user = userRepository.findById(contextProvider.getUserId());
        user.setPassword(bCryptPasswordEncoder.encode(setPasswordRequest.getPassword()));
        userRepository.save(user);
    }

    public User getUserByNickname(String nickname) {
        User user = userRepository.findByNickname(nickname);
        if (user == null) {
            String errorMessage = "Пользователь с никнеймом %s не найден"
                    .formatted(nickname);
            log.error(errorMessage);
            throw new ServiceException(errorMessage, ErrorType.USER_NOT_FOUND);
        }
        return user;
    }

    public User getUserById(int id) {
        User user = userRepository.findById(id);
        if (user == null) {
            String errorMessage = "Пользователь с id %d не найден".formatted(id);
            log.error(errorMessage);
            throw new ServiceException(errorMessage, ErrorType.USER_NOT_FOUND);
        }
        return user;
    }

    private User buildUser(RegistrationRequest registrationRequest) {
        String encodedPassword = bCryptPasswordEncoder.encode(registrationRequest.getPassword());

        return User.builder()
                .name(registrationRequest.getName())
                .nickname(registrationRequest.getNickname())
                .password(encodedPassword)
                .build();
    }
}

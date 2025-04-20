package nsu.fit.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.fit.domain.model.LoginRequest;
import nsu.fit.domain.model.MessageType;
import nsu.fit.domain.model.RegistrationRequest;
import nsu.fit.domain.model.Settings;
import nsu.fit.domain.model.User;
import nsu.fit.domain.port.SettingsRepositoryPort;
import nsu.fit.domain.port.UserRepositoryPort;
import nsu.fit.exception.AuthException;
import nsu.fit.exception.ErrorType;
import nsu.fit.exception.ServiceException;
import nsu.fit.util.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepositoryPort userRepository;
    private final SettingsService settingsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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
            String errorMessage = "Пользователь с id %s не найден"
                    .formatted(id);
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

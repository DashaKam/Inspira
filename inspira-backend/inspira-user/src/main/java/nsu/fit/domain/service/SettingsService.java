package nsu.fit.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.fit.config.ContextProvider;
import nsu.fit.domain.model.ChangeMessageTypeRequest;
import nsu.fit.domain.model.MessageType;
import nsu.fit.domain.model.Settings;
import nsu.fit.domain.port.SettingsRepositoryPort;
import nsu.fit.exception.ErrorType;
import nsu.fit.exception.ServiceException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SettingsService {
    private static final MessageType DEFAULT = MessageType.WISH;

    private final SettingsRepositoryPort settingsRepository;
    private final ContextProvider contextProvider;

    public Settings getSettingsByUserId(int userId) {
        Settings settings = settingsRepository.findByUserId(userId);
        if (settings == null) {
            String errorMessage = "Настройки пользователя с id %s не найдены"
                    .formatted(userId);
            log.error(errorMessage);
            throw new ServiceException(errorMessage, ErrorType.SETTINGS_NOT_FOUND);
        }
        return settings;
    }

    public MessageType getMessageType() {
        int userId = contextProvider.getUserId();
        Settings settings = settingsRepository.findByUserId(userId);

        if (settings == null) {
            String errorMessage = "Настройки пользователя с id %s не найдены".formatted(userId);
            log.error(errorMessage);
            throw new ServiceException(errorMessage, ErrorType.SETTINGS_NOT_FOUND);
        }

        return settings.getMessageType();
    }

    public void changeMessageType(ChangeMessageTypeRequest changeMessageTypeRequest) {
        Settings settings = settingsRepository.findByUserId(contextProvider.getUserId());
        settings.setMessageType(changeMessageTypeRequest.getMessageType());
        settingsRepository.save(settings);
    }

    public void createDefaultSettings(int userId) {
        settingsRepository.save(buildDefaultSettings(userId));
    }


    private Settings buildDefaultSettings(int userId) {
        return Settings.builder()
                .messageType(DEFAULT)
                .userId(userId)
                .build();
    }
}

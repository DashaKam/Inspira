package nsu.fit.domain.port;

import nsu.fit.domain.model.Settings;

public interface SettingsRepositoryPort {
    Settings findByUserId(int userId);

    Settings save(Settings settings);
}

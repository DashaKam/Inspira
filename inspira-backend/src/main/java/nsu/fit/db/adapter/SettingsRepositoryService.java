package nsu.fit.db.adapter;

import lombok.RequiredArgsConstructor;
import nsu.fit.db.entity.SettingsEntity;
import nsu.fit.db.mapper.SettingsEntityMapper;
import nsu.fit.db.repository.SettingsRepository;
import nsu.fit.domain.model.Settings;
import nsu.fit.domain.port.SettingsRepositoryPort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SettingsRepositoryService implements SettingsRepositoryPort {

    private final SettingsRepository settingsRepository;
    private final SettingsEntityMapper settingsEntityMapper;

    @Override
    public Settings findByUserId(int userId) {
        SettingsEntity settingsEntity = settingsRepository.findByUserId(userId);
        return settingsEntityMapper.entityToSettings(settingsEntity);
    }

    @Override
    public Settings save(Settings settings) {
        SettingsEntity settingsToSave = settingsEntityMapper.settingsToEntity(settings);
        return settingsEntityMapper.entityToSettings(settingsRepository.save(settingsToSave));
    }
}

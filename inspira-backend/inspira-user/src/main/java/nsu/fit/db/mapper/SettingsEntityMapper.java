package nsu.fit.db.mapper;

import nsu.fit.db.entity.SettingsEntity;
import nsu.fit.domain.model.Settings;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface SettingsEntityMapper {

    Settings entityToSettings(SettingsEntity settingsEntity);

    SettingsEntity settingsToEntity(Settings settings);
}

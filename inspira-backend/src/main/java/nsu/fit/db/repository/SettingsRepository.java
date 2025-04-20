package nsu.fit.db.repository;

import nsu.fit.db.entity.SettingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingsRepository extends JpaRepository<SettingsEntity, Integer> {
    SettingsEntity findByUserId(int userId);
}

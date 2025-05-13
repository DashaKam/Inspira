package nsu.fit.db.repository;

import nsu.fit.db.entity.SettingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingsRepository extends JpaRepository<SettingsEntity, Integer> {
    SettingsEntity findByUserId(int userId);
}

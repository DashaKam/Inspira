package nsu.fit.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "settings")
public class SettingsEntity {

    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "message_type")
    @Enumerated(EnumType.STRING)
    private MessageTypeDb messageType;

}

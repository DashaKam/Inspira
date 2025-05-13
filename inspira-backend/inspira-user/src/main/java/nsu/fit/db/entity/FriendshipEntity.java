package nsu.fit.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "friendship")
public class FriendshipEntity {

    @Id
    @SequenceGenerator(name = "friendship_id_generator", sequenceName = "friendship_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "friendship_id_generator", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @Column(name = "fst_user_id")
    private Integer fstUserId;

    @Column(name = "snd_user_id")
    private Integer sndUserId;

}

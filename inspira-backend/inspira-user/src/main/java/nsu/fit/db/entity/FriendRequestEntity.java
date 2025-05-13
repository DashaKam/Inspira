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
@Table(name = "friend_request")
public class FriendRequestEntity {

    @Id
    @SequenceGenerator(name = "friend_request_id_generator", sequenceName = "friend_request_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "friend_request_id_generator", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @Column(name = "sender_id")
    private Integer senderId;

    @Column(name = "receiver_id")
    private Integer receiverId;

}

package nsu.fit.db.repository;

import nsu.fit.db.entity.WishEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishRepository extends JpaRepository<WishEntity, Integer> {

    WishEntity findFirstByReceiverId(int receiverId);
    WishEntity findFirstByReceiverIdIsNullAndSenderIdNot(int senderId);
}

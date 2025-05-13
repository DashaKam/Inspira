package nsu.fit.db.repository;

import nsu.fit.db.entity.FriendRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequestEntity, Integer> {

    FriendRequestEntity findBySenderIdAndReceiverId(int senderId, int receiverId);

    void delete(FriendRequestEntity friendRequestEntity);

    List<FriendRequestEntity> findByReceiverId(int receiverId);

}

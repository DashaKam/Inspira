package nsu.fit.db.repository;

import nsu.fit.db.entity.FriendshipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<FriendshipEntity, Integer> {
    @Query("SELECT f FROM FriendshipEntity f WHERE (f.fstUserId = :fstUserId AND f.sndUserId = :sndUserId) OR (f" +
            ".fstUserId = :sndUserId AND f.sndUserId = :fstUserId)")
    FriendshipEntity findByFstUserIdAndSndUserId(@Param("fstUserId") int fstUserId, @Param("sndUserId") int sndUserId);
    @Query("SELECT f FROM FriendshipEntity f WHERE f.fstUserId = :userId OR f.sndUserId = :userId")
    List<FriendshipEntity> findByFstUserIdOrSndUserId(@Param("userId") int userId);

}

package nsu.fit.domain.port;

import nsu.fit.domain.model.Friendship;

import java.util.List;

public interface FriendshipRepositoryPort {

    Friendship save(Friendship friendship);

    Friendship findByFstUserIdAndSndUserId(int fstUserId, int sndUserId);

    List<Friendship> findByFstUserIdOrSndUserId(int userId);

}

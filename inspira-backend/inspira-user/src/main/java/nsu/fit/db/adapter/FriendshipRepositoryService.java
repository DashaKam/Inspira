package nsu.fit.db.adapter;

import lombok.RequiredArgsConstructor;
import nsu.fit.db.entity.FriendshipEntity;
import nsu.fit.db.mapper.FriendshipEntityMapper;
import nsu.fit.db.repository.FriendshipRepository;
import nsu.fit.domain.model.Friendship;
import nsu.fit.domain.port.FriendshipRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendshipRepositoryService implements FriendshipRepositoryPort {

    private final FriendshipRepository friendshipRepository;
    private final FriendshipEntityMapper friendshipEntityMapper;

    @Override
    public Friendship save(Friendship friendship) {
        FriendshipEntity friendshipEntity = friendshipEntityMapper.friendshipToEntity(friendship);
        return friendshipEntityMapper.entityToFriendship(friendshipRepository.save(friendshipEntity));
    }

    @Override
    public Friendship findByFstUserIdAndSndUserId(int fstUserId, int sndUserId) {
        FriendshipEntity friendshipEntity = friendshipRepository.findByFstUserIdAndSndUserId(fstUserId, sndUserId);
        return friendshipEntityMapper.entityToFriendship(friendshipEntity);
    }

    @Override
    public List<Friendship> findByFstUserIdOrSndUserId(int userId) {
        List<FriendshipEntity> friendshipEntities = friendshipRepository.findByFstUserIdOrSndUserId(userId);
        return friendshipEntityMapper.entitiesToFriendships(friendshipEntities);
    }
}

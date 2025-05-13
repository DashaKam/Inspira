package nsu.fit.db.adapter;

import lombok.RequiredArgsConstructor;
import nsu.fit.db.entity.FriendRequestEntity;
import nsu.fit.db.mapper.FriendRequestEntityMapper;
import nsu.fit.db.repository.FriendRequestRepository;
import nsu.fit.domain.model.FriendRequest;
import nsu.fit.domain.port.FriendRequestRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendRequestRepositoryService implements FriendRequestRepositoryPort {

    private final FriendRequestEntityMapper friendRequestEntityMapper;
    private final FriendRequestRepository friendRequestRepository;

    @Override
    public FriendRequest save(FriendRequest friendRequest) {
        FriendRequestEntity friendRequestEntity = friendRequestEntityMapper.friendRequestToEntity(friendRequest);
        return friendRequestEntityMapper.entityToFriendRequest(friendRequestRepository.save(friendRequestEntity));
    }

    @Override
    public FriendRequest findBySenderIdAndReceiverId(int senderId, int receiverId) {
        FriendRequestEntity friendRequestEntity = friendRequestRepository
                .findBySenderIdAndReceiverId(senderId, receiverId);
        return friendRequestEntityMapper.entityToFriendRequest(friendRequestEntity);

    }

    @Override
    public List<FriendRequest> findByReceiverId(int receiverId) {
        List<FriendRequestEntity> friendRequestEntities = friendRequestRepository.findByReceiverId(receiverId);
        return friendRequestEntityMapper.entitiesToFriendRequests(friendRequestEntities);
    }

    @Override
    public void delete(FriendRequest friendRequest) {
        FriendRequestEntity friendRequestEntity = friendRequestEntityMapper.friendRequestToEntity(friendRequest);
        friendRequestRepository.delete(friendRequestEntity);
    }
}

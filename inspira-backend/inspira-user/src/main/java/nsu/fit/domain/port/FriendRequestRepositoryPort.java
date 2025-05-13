package nsu.fit.domain.port;

import nsu.fit.domain.model.FriendRequest;

import java.util.List;

public interface FriendRequestRepositoryPort {

    FriendRequest save(FriendRequest friendRequest);

    FriendRequest findBySenderIdAndReceiverId(int senderId, int receiverId);

    List<FriendRequest> findByReceiverId(int receiverId);

    void delete(FriendRequest friendRequest);

}

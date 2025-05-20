package nsu.fit.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.fit.config.ContextProvider;
import nsu.fit.domain.model.UiUser;
import nsu.fit.domain.model.FriendRequest;
import nsu.fit.domain.model.Friendship;
import nsu.fit.domain.model.UiFriendRequest;
import nsu.fit.domain.port.FriendRequestRepositoryPort;
import nsu.fit.domain.port.FriendshipRepositoryPort;
import nsu.fit.exception.ErrorType;
import nsu.fit.exception.ServiceException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FriendService {

    private final FriendRequestRepositoryPort friendRequestRepository;
    private final FriendshipRepositoryPort friendshipRepository;
    private final ContextProvider contextProvider;
    private final UserService userService;

    public void saveFriendRequest(UiFriendRequest uiFriendRequest) {
        int receiverId = userService.getUserByNickname(uiFriendRequest.getUserNickname()).getId();
        int senderId = contextProvider.getUserId();

        if (receiverId == senderId) {
            String errorMessage = "Невозможно отправить запрос на добавление в друзья самому себе";
            log.error(errorMessage);
            throw new ServiceException(errorMessage, ErrorType.SELF_FRIEND_REQUEST);
        }

        if (friendRequestRepository.findBySenderIdAndReceiverId(senderId, receiverId) != null) {
            String errorMessage = "Запрос на добавление в друзья уже отправлен и ожидает ответа";
            log.error(errorMessage);
            throw new ServiceException(errorMessage, ErrorType.FRIEND_REQUEST_ALREADY_PENDING);
        }

        FriendRequest friendRequest = FriendRequest
                .builder()
                .receiverId(receiverId)
                .senderId(senderId)
                .build();

        friendRequestRepository.save(friendRequest);
    }

    public void declineFriendRequest(UiFriendRequest uiFriendRequest) {
        friendRequestRepository.delete(getFriendRequest(uiFriendRequest));

    }

    public void acceptFriendRequest(UiFriendRequest uiFriendRequest) {
        FriendRequest friendRequest = getFriendRequest(uiFriendRequest);
        friendRequestRepository.delete(friendRequest);

        if (friendshipRepository.findByFstUserIdAndSndUserId(friendRequest.getSenderId(),
                friendRequest.getReceiverId()) != null) {
            String errorMessage = "Пользователи уже друг у друга в друзьях";
            log.error(errorMessage);
            throw new ServiceException(errorMessage, ErrorType.FRIENDSHIP_ALREADY_EXIST);
        }

        Friendship friendship = Friendship.builder()
                .fstUserId(friendRequest.getSenderId())
                .sndUserId(friendRequest.getReceiverId())
                .build();

        friendshipRepository.save(friendship);
    }

    public List<UiUser> getFriendList() {
        List<Friendship> friendships = friendshipRepository.findByFstUserIdOrSndUserId(contextProvider.getUserId());
        return friendshipsToFriends(friendships);
    }

    public List<UiFriendRequest> getFriendRequestsList() {
        List<FriendRequest> friendRequests = friendRequestRepository.findByReceiverId(contextProvider.getUserId());
        return friendRequestToUiFriendRequests(friendRequests);
    }

    private FriendRequest getFriendRequest(UiFriendRequest uiFriendRequest) {
        int senderId = userService.getUserByNickname(uiFriendRequest.getUserNickname()).getId();
        int receiverId = contextProvider.getUserId();

        FriendRequest friendRequest = friendRequestRepository.findBySenderIdAndReceiverId(senderId, receiverId);

        if (friendRequest == null) {
            String errorMessage = "Запрос на добавление в друзья, отправленный пользователем %s, не найден"
                    .formatted(uiFriendRequest.getUserNickname());
            log.error(errorMessage);
            throw new ServiceException(errorMessage, ErrorType.FRIEND_REQUEST_NOT_FOUND);
        }

        return friendRequest;
    }

    private List<UiUser> friendshipsToFriends(List<Friendship> friendships) {
        List<UiUser> uiUsers = new ArrayList<>();
        int userId = contextProvider.getUserId();

        for (Friendship friendship : friendships) {
            String friendNickname = userService.getUserById(getFriendIdFromFriendship(friendship, userId)).getNickname();
            uiUsers.add(UiUser.builder().nickname(friendNickname).build());
        }

        return uiUsers;
    }

    private List<UiFriendRequest> friendRequestToUiFriendRequests(List<FriendRequest> friendRequests) {
        List<UiFriendRequest> uiFriendRequests = new ArrayList<>();

        for (FriendRequest friendRequest : friendRequests) {
            String userNickname = userService.getUserById(friendRequest.getSenderId()).getNickname();
            uiFriendRequests.add(UiFriendRequest.builder().userNickname(userNickname).build());
        }

        return uiFriendRequests;
    }

    private int getFriendIdFromFriendship(Friendship friendship, int userId) {
        if (friendship.getFstUserId() != userId) {
            return friendship.getFstUserId();
        } else {
            return friendship.getSndUserId();
        }
    }
}

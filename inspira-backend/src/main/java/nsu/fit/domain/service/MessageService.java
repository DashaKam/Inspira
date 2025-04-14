package nsu.fit.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.fit.domain.model.User;
import nsu.fit.domain.model.Wish;
import nsu.fit.domain.model.WishRequest;
import nsu.fit.domain.port.WishRepositoryPort;
import nsu.fit.util.SecurityUtil;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {

    private final UserService userService;
    private final ModerationService moderationService;
    private final WishRepositoryPort wishRepository;

    public boolean createWish(WishRequest wishRequest) {
        boolean isWishAllowed = moderationService.moderate(wishRequest.getMessage()).join();

        if (isWishAllowed) {
            wishRepository.save(createWishStateless(wishRequest));
        }

        return isWishAllowed;
    }

    private Wish createWishStateless(WishRequest wishRequest) {
        Wish.WishBuilder wishBuilder = Wish.builder();

        if (Boolean.FALSE.equals(wishRequest.getAnonymous())) {
            User sender = SecurityUtil.getCurrentUser();
            wishBuilder.senderId(sender.getId());
        }

        if (wishRequest.getReceiverNickname() != null) {
            User receiver = userService.getUserByNickname(wishRequest.getReceiverNickname());
            wishBuilder.receiverId(receiver.getId());
        }

        return wishBuilder
                .message(wishRequest.getMessage())
                .build();
    }
}

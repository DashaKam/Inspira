package nsu.fit.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.fit.config.ContextProvider;
import nsu.fit.domain.model.Message;
import nsu.fit.domain.model.MessageType;
import nsu.fit.domain.model.ModerateWishRequest;
import nsu.fit.domain.model.ModerateWishResponse;
import nsu.fit.domain.model.User;
import nsu.fit.domain.model.UserSearchFilter;
import nsu.fit.domain.model.Wish;
import nsu.fit.domain.model.WishRequest;
import nsu.fit.domain.port.LlmClientPort;
import nsu.fit.domain.port.UserClientPort;
import nsu.fit.domain.port.WishRepositoryPort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {

    private static final String DEFAULT_SENDER = "Inspira";

    private final UserClientPort userService;
    private final LlmClientPort llmService;

    private final WishRepositoryPort wishRepository;

    private final ContextProvider contextProvider;

    public ModerateWishResponse createWish(WishRequest wishRequest) {
        ModerateWishRequest moderateWishRequest = ModerateWishRequest
                .builder()
                .wish(wishRequest.getMessage())
                .build();

        ModerateWishResponse moderateWishResponse = llmService.moderateWish(moderateWishRequest);

        if (moderateWishResponse.getIsWishAllowed()) {
            wishRepository.save(createWishStateless(wishRequest));
        }

        return moderateWishResponse;
    }

    public Message getMessage() {
        MessageType messageType = userService.getMessageType();

        if (messageType == MessageType.WISH) {
            Message message = pollWish(contextProvider.getUserId());

            if (message != null) {
                return message;
            }

            return Message.builder()
                    .messageText(llmService.generateMessage(MessageType.WISH).getText())
                    .senderNickname(DEFAULT_SENDER)
                    .messageType(MessageType.WISH)
                    .build();
        }

        return Message.builder()
                .messageText(llmService.generateMessage(MessageType.MOTIVATIONAL_QUOTE).getText())
                .messageType(MessageType.MOTIVATIONAL_QUOTE)
                .build();
    }

    private Message pollWish(int currUserId) {
        Wish wish = wishRepository.findFirstByReceiverId(currUserId);

        if (wish == null) {
            wish = wishRepository.findFirstByReceiverIdIsNullAndSenderIdNot(currUserId);

            if (wish == null) {
                return null;
            }
        }

        wishRepository.deleteById(wish.getId());

        Message.MessageBuilder messageBuilder = Message.builder();

        if (!wish.getAnonymous()) {
            User user = userService.searchUser(UserSearchFilter
                    .builder()
                    .userId(wish.getSenderId())
                    .build());
            messageBuilder.senderNickname(user.getNickname());
        }

        return messageBuilder
                .messageType(MessageType.WISH)
                .messageText(wish.getMessage())
                .build();
    }

    private Wish createWishStateless(WishRequest wishRequest) {
        Wish.WishBuilder wishBuilder = Wish.builder();
        int senderId = contextProvider.getUserId();

        if (wishRequest.getReceiverNickname() != null) {
            User receiver = userService.searchUser(UserSearchFilter
                    .builder()
                    .userNickname(wishRequest.getReceiverNickname())
                    .build());
            wishBuilder.receiverId(receiver.getId());
        }

        return wishBuilder
                .senderId(senderId)
                .message(wishRequest.getMessage())
                .anonymous(wishRequest.getAnonymous())
                .build();
    }
}

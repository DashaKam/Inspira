package nsu.fit.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.fit.domain.model.Message;
import nsu.fit.domain.model.MessageType;
import nsu.fit.domain.model.Settings;
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

    private static final String DEFAULT_SENDER = "Inspira";

    private final UserService userService;
    private final SettingsService settingsService;
    private final AiModelService aiModelService;
    private final WishRepositoryPort wishRepository;

    public boolean createWish(WishRequest wishRequest) {
        boolean isWishAllowed = aiModelService.moderateWish(wishRequest.getMessage()).join();

        if (isWishAllowed) {
            wishRepository.save(createWishStateless(wishRequest));
        }

        return isWishAllowed;
    }

    public Message getMessage() {
        User user = SecurityUtil.getCurrentUser();
        Settings settings = settingsService.getSettingsByUserId(user.getId());

        if (settings.getMessageType() == MessageType.WISH) {
            Message message = pollWish(user.getId());

            if (message != null) {
                return message;
            }

            return Message.builder()
                    .messageText(aiModelService.getAiMessage(MessageType.WISH))
                    .senderNickname(DEFAULT_SENDER)
                    .messageType(MessageType.WISH)
                    .build();
        }

        return Message.builder()
                .messageText(aiModelService.getAiMessage(MessageType.MOTIVATIONAL_QUOTE))
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
            User user = userService.getUserById(wish.getSenderId());
            messageBuilder.senderNickname(user.getNickname());
        }

        return messageBuilder
                .messageType(MessageType.WISH)
                .messageText(wish.getMessage())
                .build();
    }

    private Wish createWishStateless(WishRequest wishRequest) {
        Wish.WishBuilder wishBuilder = Wish.builder();
        User sender = SecurityUtil.getCurrentUser();

        if (wishRequest.getReceiverNickname() != null) {
            User receiver = userService.getUserByNickname(wishRequest.getReceiverNickname());
            wishBuilder.receiverId(receiver.getId());
        }

        return wishBuilder
                .senderId(sender.getId())
                .message(wishRequest.getMessage())
                .anonymous(wishRequest.getAnonymous())
                .build();
    }
}

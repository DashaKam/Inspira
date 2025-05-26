package nsu.fit.domain.service;

import lombok.RequiredArgsConstructor;
import nsu.fit.domain.model.Message;
import nsu.fit.domain.model.MessageType;
import nsu.fit.domain.model.ModerateWishRequest;
import nsu.fit.domain.model.ModerateWishResponse;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class LlmService {

    private final GigaChatService gigaChatService;
    private final ToxicityClassifierService toxicityClassifierService;

    public ModerateWishResponse moderateWish(ModerateWishRequest moderateWishRequest) {
        Boolean isWishAllowed = isPositiveToneAndCleanLanguage(moderateWishRequest.getWish()).join();

        return ModerateWishResponse
                .builder()
                .isWishAllowed(isWishAllowed)
                .build();
    }

    public Message getMessage(MessageType messageType) {
        return Message
                .builder()
                .text(gigaChatService.generateMessage(messageType))
                .build();
    }

   private CompletableFuture<Boolean> isPositiveToneAndCleanLanguage(String wish) {
        CompletableFuture<Boolean> isPositiveToneFuture = isPositiveTone(wish);
        CompletableFuture<Boolean> isCleanLanguageFuture = isCleanLanguage(wish);

        return isCleanLanguageFuture
                .thenCombine(isPositiveToneFuture, (isCleanLanguage, isPositiveTone) -> isCleanLanguage && isPositiveTone);
    }

    private CompletableFuture<Boolean> isPositiveTone(String wish) {
        return CompletableFuture.supplyAsync(() -> gigaChatService.isPositiveTone(wish));
    }

    private CompletableFuture<Boolean> isCleanLanguage(String wish) {
        return CompletableFuture.supplyAsync(() -> toxicityClassifierService.isCleanLanguage(wish));
    }
}

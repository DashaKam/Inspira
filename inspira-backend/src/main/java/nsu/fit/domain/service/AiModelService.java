package nsu.fit.domain.service;

import lombok.RequiredArgsConstructor;
import nsu.fit.domain.model.MessageType;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class AiModelService {

    private final GigaChatService gigaChatService;
    private final ToxicityClassifierService toxicityClassifierService;

    public CompletableFuture<Boolean> moderateWish(String wish) {
        CompletableFuture<Boolean> isPositiveToneFuture = isPositiveTone(wish);
        CompletableFuture<Boolean> isCleanLanguageFuture = isCleanLanguage(wish);

        return isCleanLanguageFuture
                .thenCombine(isPositiveToneFuture, (isCleanLanguage, isPositiveTone) -> isCleanLanguage && isPositiveTone);
    }

    public String getAiMessage(MessageType messageType) {
        return gigaChatService.generateMessage(messageType);
    }

    private CompletableFuture<Boolean> isPositiveTone(String wish) {
        return CompletableFuture.supplyAsync(() -> gigaChatService.isPositiveTone(wish));
    }

    private CompletableFuture<Boolean> isCleanLanguage(String wish) {
        return CompletableFuture.supplyAsync(() -> toxicityClassifierService.isCleanLanguage(wish));
    }
}

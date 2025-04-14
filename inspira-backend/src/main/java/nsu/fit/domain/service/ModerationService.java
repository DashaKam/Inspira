package nsu.fit.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ModerationService {

    private final GigaChatService gigaChatService;
    private final ToxicityClassifierService toxicityClassifierService;

    public CompletableFuture<Boolean> moderate(String wish) {
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

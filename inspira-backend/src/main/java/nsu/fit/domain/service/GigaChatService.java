package nsu.fit.domain.service;

import chat.giga.client.GigaChatClient;
import chat.giga.model.ModelName;
import chat.giga.model.completion.ChatMessage;
import chat.giga.model.completion.CompletionRequest;
import chat.giga.model.completion.CompletionResponse;
import lombok.extern.slf4j.Slf4j;
import nsu.fit.domain.model.MessageType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GigaChatService {
    private final GigaChatClient client;

    @Value("${gigachat.moderation_prompt}")
    private String moderationPrompt;

    @Value("${gigachat.wish_generation_prompt}")
    private String wishGenerationPrompt;

    @Value("${gigachat.quote_generation_prompt}")
    private String quoteGenerationPrompt;

    public GigaChatService(GigaChatClient client) {
        this.client = client;
    }

    public boolean isPositiveTone(String wish) {
        CompletionResponse response = client.completions(CompletionRequest.builder()
                .model(ModelName.GIGA_CHAT)
                .message(ChatMessage.builder()
                        .content(moderationPrompt + "\"" + wish + "\"")
                        .role(ChatMessage.Role.USER)
                        .build())
                .build());

        String isPositiveTone = response.choices().get(0).message().content();

        if ("true".equalsIgnoreCase(isPositiveTone)) {
            return true;
        } else if ("false".equalsIgnoreCase(isPositiveTone)) {
            return false;
        } else {
            log.error("Ожидался ответ true / false. Ответ модели: {}", isPositiveTone);
            return false;
        }
    }

    public String generateMessage(MessageType messageType) {
        String prompt;

        if (messageType.equals(MessageType.WISH)) {
            prompt = wishGenerationPrompt;
        } else {
            prompt = quoteGenerationPrompt;
        }

        CompletionResponse response = client.completions(CompletionRequest.builder()
                .model(ModelName.GIGA_CHAT)
                .message(ChatMessage.builder()
                        .content(prompt)
                        .role(ChatMessage.Role.USER)
                        .build())
                .build());

        return response.choices().get(0).message().content();
    }

}

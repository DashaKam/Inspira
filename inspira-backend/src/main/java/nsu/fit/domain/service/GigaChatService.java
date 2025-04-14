package nsu.fit.domain.service;

import chat.giga.client.GigaChatClient;
import chat.giga.model.ModelName;
import chat.giga.model.completion.ChatMessage;
import chat.giga.model.completion.CompletionRequest;
import chat.giga.model.completion.CompletionResponse;
import lombok.extern.slf4j.Slf4j;
import nsu.fit.exception.ErrorType;
import nsu.fit.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GigaChatService {
    private final GigaChatClient client;

    @Value("${gigachat.prompt}")
    private String prompt;

    public GigaChatService(GigaChatClient client) {
        this.client = client;
    }

    public boolean isPositiveTone(String wish) {
        CompletionResponse response = client.completions(CompletionRequest.builder()
                .model(ModelName.GIGA_CHAT)
                .message(ChatMessage.builder()
                        .content(prompt + "\"" + wish + "\"")
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

}

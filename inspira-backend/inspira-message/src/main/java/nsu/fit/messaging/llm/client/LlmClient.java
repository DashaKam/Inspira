package nsu.fit.messaging.llm.client;

import nsu.fit.messaging.llm.dto.GeneratedMessageDto;
import nsu.fit.messaging.llm.dto.MessageTypeDto;
import nsu.fit.messaging.llm.dto.ModerateWishResponseDto;
import nsu.fit.messaging.llm.dto.ModerateWishRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "llm-service",
        url = "${llm-service.url}"
)
public interface LlmClient {
    @PostMapping("/api/llm/moderate-wish")
    ModerateWishResponseDto moderateWish(@RequestBody ModerateWishRequestDto moderateWishRequestDto);

    @PostMapping("/api/llm/generate-message")
    GeneratedMessageDto generateMessage(@RequestBody MessageTypeDto messageTypeDto);
}

package nsu.fit.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import nsu.fit.domain.model.Message;
import nsu.fit.domain.model.ModerateWishRequest;
import nsu.fit.domain.service.LlmService;
import nsu.fit.web.dto.MessageDto;
import nsu.fit.web.dto.MessageTypeDto;
import nsu.fit.web.dto.ModerateWishRequestDto;
import nsu.fit.web.dto.ModerateWishResponseDto;
import nsu.fit.web.mapper.MessageDtoMapper;
import nsu.fit.web.mapper.MessageTypeDtoMapper;
import nsu.fit.web.mapper.ModerateWishRequestDtoMapper;
import nsu.fit.web.mapper.ModerateWishResponseDtoMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/llm")
public class LlmController {

    private final LlmService llmService;

    private final ModerateWishRequestDtoMapper moderateWishRequestDtoMapper;
    private final ModerateWishResponseDtoMapper moderateWishResponseDtoMapper;
    private final MessageTypeDtoMapper messageTypeDtoMapper;
    private final MessageDtoMapper messageDtoMapper;

    @PostMapping("/moderate-wish")
    @Operation(
            tags = "Внутренние API",
            summary = "Модерация пожелания",
            description = "Фильтрация пожеланий с целью выявления ненормативной лексики и негативного тона"
    )
    public ModerateWishResponseDto moderateWish(@RequestBody ModerateWishRequestDto moderateWishRequestDto) {
        ModerateWishRequest moderateWishRequest = moderateWishRequestDtoMapper
                .dtoToModerateWishRequest(moderateWishRequestDto);

        return moderateWishResponseDtoMapper.moderateWishResponseToDto(llmService.moderateWish(moderateWishRequest));
    }

    @PostMapping("/generate-message")
    @Operation(
            tags = "Внутренние API",
            summary = "Генерация сообщения",
            description = "Генерация пожелания / мотивационной цитаты"
    )
    public MessageDto generateMessage(@RequestBody MessageTypeDto messageTypeDto) {
        Message message = llmService.getMessage(messageTypeDtoMapper.dtoToMessageType(messageTypeDto));
        return messageDtoMapper.messageToDto(message);
    }
}

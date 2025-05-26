package nsu.fit.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import nsu.fit.domain.model.Message;
import nsu.fit.domain.model.WishRequest;
import nsu.fit.domain.service.MessageService;
import nsu.fit.web.dto.MessageResponseDto;
import nsu.fit.web.dto.WishRequestDto;
import nsu.fit.web.dto.WishResponseDto;
import nsu.fit.web.mapper.MessageDtoMapper;
import nsu.fit.web.mapper.WishDtoMapper;
import nsu.fit.web.mapper.WishResponseDtoMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    private final WishDtoMapper wishDtoMapper;
    private final MessageDtoMapper messageDtoMapper;
    private final WishResponseDtoMapper wishResponseDtoMapper;

    @GetMapping("/message")
    @Operation(
            tags = "Внешние API",
            summary = "Получение сообщения",
            description = "Получение сообщения: мотивационной цитаты (сгенерированной нейросетью) или пожелания " +
                    "(отправленного другим пользователем или сгенерированного нейросетью)"
    )
    public MessageResponseDto getMessage() {
        Message message = messageService.getMessage();
        return messageDtoMapper.messageToDto(message);
    }

    @PostMapping("/send-wish")
    @Operation(
            tags = "Внешние API",
            summary = "Отправление пожелания",
            description = "Отправление пожелания другому пользователю"
    )
    public WishResponseDto sendWish(@RequestBody WishRequestDto wishRequestDto) {
        WishRequest wishRequest = wishDtoMapper.dtoToWishRequest(wishRequestDto);
        return wishResponseDtoMapper.moderateWishResponseToDto(messageService.createWish(wishRequest));
    }
}

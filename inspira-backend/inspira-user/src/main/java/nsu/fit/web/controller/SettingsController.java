package nsu.fit.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nsu.fit.domain.model.ChangeMessageTypeRequest;
import nsu.fit.domain.model.MessageType;
import nsu.fit.domain.service.SettingsService;
import nsu.fit.web.dto.ChangeMessageTypeRequestDto;
import nsu.fit.web.dto.MessageTypeDto;
import nsu.fit.web.mapper.MessageTypeDtoMapper;
import nsu.fit.web.mapper.ChangeMessageTypeDtoMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class SettingsController {
    private final SettingsService settingsService;
    private final ChangeMessageTypeDtoMapper changeMessageTypeDtoMapper;
    private final MessageTypeDtoMapper messageTypeDtoMapper;

    @PostMapping("/change-message-type")
    @Operation(
            tags = "Внешние API",
            summary = "Изменение типа получаемых сообщений"
    )
    public void changeMessageType(@RequestBody @Valid ChangeMessageTypeRequestDto changeMessageTypeRequestDto) {
        ChangeMessageTypeRequest changeMessageTypeRequest =
                changeMessageTypeDtoMapper.dtoToChangeMessageTypeRequest(changeMessageTypeRequestDto);
        settingsService.changeMessageType(changeMessageTypeRequest);
    }

    @GetMapping("/message-type")
    @Operation(
            tags = "Внутренние API",
            summary = "Получение типа сообщений, предпочитаемых пользователем"
    )
    public MessageTypeDto getMessageType() {
        MessageType messageType = settingsService.getMessageType();
        return messageTypeDtoMapper.messageTypeToDto(messageType);
    }
}

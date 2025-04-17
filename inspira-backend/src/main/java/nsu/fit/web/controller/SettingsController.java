package nsu.fit.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nsu.fit.domain.model.ChangeMessageTypeRequest;
import nsu.fit.domain.service.SettingsService;
import nsu.fit.web.dto.ChangeMessageTypeRequestDto;
import nsu.fit.web.mapper.ChangeMessageTypeDtoMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SettingsController {
    private final SettingsService settingsService;
    private final ChangeMessageTypeDtoMapper changeMessageTypeDtoMapper;

    @PostMapping("/change-message-type")
    public void changeMessageType(@RequestBody @Valid ChangeMessageTypeRequestDto changeMessageTypeRequestDto) {
        ChangeMessageTypeRequest changeMessageTypeRequest =
                changeMessageTypeDtoMapper.dtoToChangeMessageTypeRequest(changeMessageTypeRequestDto);
        settingsService.changeMessageType(changeMessageTypeRequest);
    }
}

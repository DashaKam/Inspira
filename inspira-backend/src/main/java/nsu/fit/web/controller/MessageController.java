package nsu.fit.web.controller;

import lombok.RequiredArgsConstructor;
import nsu.fit.domain.model.User;
import nsu.fit.domain.model.WishRequest;
import nsu.fit.domain.service.MessageService;
import nsu.fit.web.dto.WishRequestDto;
import nsu.fit.web.dto.WishResponseDto;
import nsu.fit.web.mapper.WishDtoMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MessageController {
    private final MessageService messageService;
    private final WishDtoMapper wishDtoMapper;
    @GetMapping("/message")
    public void getMessage() {

    }

    @PostMapping("/send-wish")
    public WishResponseDto sendWish(@RequestBody WishRequestDto wishRequestDto) {
        WishRequest wishRequest = wishDtoMapper.dtoToWishRequest(wishRequestDto);
        Boolean isWishAllowed = messageService.createWish(wishRequest);
        return new WishResponseDto(isWishAllowed);
    }
}

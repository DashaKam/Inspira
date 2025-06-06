package nsu.fit.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nsu.fit.domain.service.FriendService;
import nsu.fit.web.dto.UiFriendRequestDto;
import nsu.fit.web.dto.UiUserDto;
import nsu.fit.web.mapper.UiUserDtoMapper;
import nsu.fit.web.mapper.UiFriendRequestDtoMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class FriendController {

    private final FriendService friendService;

    private final UiFriendRequestDtoMapper uiFriendRequestDtoMapper;
    private final UiUserDtoMapper uiUserDtoMapper;

    @PostMapping("/send-friend-request")
    @Operation(
            tags = "Внешние API",
            summary = "Отправление запроса на добавление в друзья"
    )
    public void sendFriendRequest(@RequestBody @Valid UiFriendRequestDto uiFriendRequestDto) {
        friendService.saveFriendRequest(uiFriendRequestDtoMapper.dtoToUiFriendRequest(uiFriendRequestDto));
    }

    @PostMapping("/accept-request")
    @Operation(
            tags = "Внешние API",
            summary = "Принятие запроса на добавление в друзья"
    )
    public void acceptFriendRequest(@RequestBody @Valid UiFriendRequestDto uiFriendRequestDto) {
        friendService.acceptFriendRequest(uiFriendRequestDtoMapper.dtoToUiFriendRequest(uiFriendRequestDto));
    }

    @PostMapping("/decline-request")
    @Operation(
            tags = "Внешние API",
            summary = "Отклонение запроса на добавление в друзья"
    )
    public void declineFriendRequest(@RequestBody @Valid UiFriendRequestDto uiFriendRequestDto) {
        friendService.declineFriendRequest(uiFriendRequestDtoMapper.dtoToUiFriendRequest(uiFriendRequestDto));
    }

    @GetMapping("/friendlist")
    @Operation(
            tags = "Внешние API",
            summary = "Получение списка друзей",
            description = "Получение списка друзей текущего пользователя"
    )
    public List<UiUserDto> getFriendlist() {
       return uiUserDtoMapper.friendListToFriendDtoList(friendService.getFriendList());
    }

    @GetMapping("/friend-requests-list")
    @Operation(
            tags = "Внешние API",
            summary = "Получение списка запросов на добавление в друзья",
            description = "Получение списка запросов на добавление в друзья, отправленных текущему пользователю"
    )
    public List<UiFriendRequestDto> getFriendRequestsList() {
        return uiFriendRequestDtoMapper.uiFriendRequestListToDtoList(friendService.getFriendRequestsList());
    }

}

package nsu.fit.messaging.user.client;

import nsu.fit.messaging.user.dto.MessageTypeDto;
import nsu.fit.messaging.user.dto.UserDto;
import nsu.fit.messaging.user.dto.UserSearchFilterDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "user-service",
        url = "${user-service.url}"
)
public interface UserClient {
    @GetMapping("/api/users/message-type")
    MessageTypeDto getMessageType();

    @GetMapping("/api/users/search")
    UserDto searchUser(@SpringQueryMap UserSearchFilterDto userSearchFilterDto);
}

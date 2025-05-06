package nsu.fit.messaging.user.adapter;

import lombok.RequiredArgsConstructor;
import nsu.fit.domain.model.MessageType;
import nsu.fit.domain.model.User;
import nsu.fit.domain.model.UserSearchFilter;
import nsu.fit.domain.port.UserClientPort;
import nsu.fit.messaging.user.client.UserClient;
import nsu.fit.messaging.user.dto.MessageTypeDto;
import nsu.fit.messaging.user.dto.UserSearchFilterDto;
import nsu.fit.messaging.user.mapper.UserClientMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserClientAdapter implements UserClientPort {

    private final UserClient userClient;
    private final UserClientMapper userClientMapper;

    @Override
    public MessageType getMessageType() {
        MessageTypeDto messageTypeDto = userClient.getMessageType();
        return userClientMapper.dtoToMessageType(messageTypeDto);
    }

    @Override
    public User searchUser(UserSearchFilter userSearchFilter) {
        UserSearchFilterDto userSearchFilterDto = userClientMapper.userSearchFilterToDto(userSearchFilter);
        return userClientMapper.dtoToUser(userClient.searchUser(userSearchFilterDto));
    }
}

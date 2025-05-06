package nsu.fit.domain.port;

import nsu.fit.domain.model.MessageType;
import nsu.fit.domain.model.User;
import nsu.fit.domain.model.UserSearchFilter;

public interface UserClientPort {

    MessageType getMessageType();

    User searchUser(UserSearchFilter userSearchFilter);

}

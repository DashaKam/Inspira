package nsu.fit.domain.port;

import nsu.fit.domain.model.GeneratedMessage;
import nsu.fit.domain.model.MessageType;
import nsu.fit.domain.model.ModerateWishRequest;
import nsu.fit.domain.model.ModerateWishResponse;

public interface LlmClientPort {

    ModerateWishResponse moderateWish(ModerateWishRequest moderateWishRequest);

    GeneratedMessage generateMessage(MessageType messageType);

}

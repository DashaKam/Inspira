package nsu.fit.domain.port;

import nsu.fit.db.entity.WishEntity;
import nsu.fit.domain.model.Wish;
import org.springframework.data.repository.query.Param;

public interface WishRepositoryPort {
    Wish save(Wish wish);

    Wish findFirstByReceiverId(int receiverId);

    void deleteById(int id);

    Wish findFirstByReceiverIdIsNullAndSenderIdNot(int senderId);
}

package nsu.fit.db.mapper;

import nsu.fit.db.entity.FriendshipEntity;
import nsu.fit.domain.model.Friendship;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface FriendshipEntityMapper {

    Friendship entityToFriendship(FriendshipEntity friendshipEntity);

    FriendshipEntity friendshipToEntity(Friendship friendship);

    List<Friendship> entitiesToFriendships(List<FriendshipEntity> friendshipEntities);

    List<FriendshipEntity> friendshipsToEntities(List<Friendship> friendships);

}

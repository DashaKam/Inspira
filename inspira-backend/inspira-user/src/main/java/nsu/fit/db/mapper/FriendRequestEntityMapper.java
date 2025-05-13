package nsu.fit.db.mapper;

import nsu.fit.db.entity.FriendRequestEntity;
import nsu.fit.domain.model.FriendRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface FriendRequestEntityMapper {

    FriendRequest entityToFriendRequest(FriendRequestEntity friendRequestEntity);

    FriendRequestEntity friendRequestToEntity(FriendRequest friendRequest);

    List<FriendRequest> entitiesToFriendRequests(List<FriendRequestEntity> friendRequests);

}

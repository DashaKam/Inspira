package nsu.fit.db.mapper;

import nsu.fit.db.entity.UserEntity;
import nsu.fit.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserEntityMapper {

    User entityToUser(UserEntity user);

    UserEntity userToEntity(User user);

}

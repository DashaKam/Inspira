package nsu.fit.db.mapper;

import javax.annotation.processing.Generated;
import nsu.fit.db.entity.UserEntity;
import nsu.fit.domain.model.User;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-01T21:53:39+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 20.0.2.1 (Amazon.com Inc.)"
)
@Component
public class UserEntityMapperImpl implements UserEntityMapper {

    @Override
    public User entityToUser(UserEntity user) {
        if ( user == null ) {
            return null;
        }

        User.UserBuilder user1 = User.builder();

        user1.id( user.getId() );
        user1.nickname( user.getNickname() );
        user1.name( user.getName() );
        user1.password( user.getPassword() );

        return user1.build();
    }

    @Override
    public UserEntity userToEntity(User user) {
        if ( user == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setId( user.getId() );
        userEntity.setNickname( user.getNickname() );
        userEntity.setName( user.getName() );
        userEntity.setPassword( user.getPassword() );

        return userEntity;
    }
}

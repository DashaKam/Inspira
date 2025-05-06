package nsu.fit.db.adapter;

import lombok.RequiredArgsConstructor;
import nsu.fit.db.entity.UserEntity;
import nsu.fit.db.mapper.UserEntityMapper;
import nsu.fit.db.repository.UserRepository;
import nsu.fit.domain.model.User;
import nsu.fit.domain.model.UserSearchFilter;
import nsu.fit.domain.port.UserRepositoryPort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRepositoryService implements UserRepositoryPort {

    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public User findByNickname(String nickname) {
        UserEntity userEntity = userRepository.findByNickname(nickname);
        return userEntityMapper.entityToUser(userEntity);
    }

    @Override
    public User findById(int id) {
        UserEntity userEntity = userRepository.findById(id);
        return userEntityMapper.entityToUser(userEntity);
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    @Override
    public User save(User user) {
        UserEntity userToSave = userEntityMapper.userToEntity(user);
        return userEntityMapper.entityToUser(userRepository.save(userToSave));
    }

    @Override
    public User search(UserSearchFilter userSearchFilter) {
        UserEntity userEntity = userRepository.findByIdOrNickname(
                userSearchFilter.getUserId(),
                userSearchFilter.getUserNickname());
        return userEntityMapper.entityToUser(userEntity);
    }
}

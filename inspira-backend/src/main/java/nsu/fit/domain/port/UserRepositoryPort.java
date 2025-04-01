package nsu.fit.domain.port;

import nsu.fit.domain.model.User;

public interface UserRepositoryPort {

    User findByNickname(String nickname);

    boolean existsByNickname(String nickname);

    User save(User user);

}

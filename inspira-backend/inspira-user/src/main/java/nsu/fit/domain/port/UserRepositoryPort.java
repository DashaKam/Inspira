package nsu.fit.domain.port;

import nsu.fit.domain.model.User;
import nsu.fit.domain.model.UserSearchFilter;

public interface UserRepositoryPort {

    User findByNickname(String nickname);

    User findById(int id);

    boolean existsByNickname(String nickname);

    User save(User user);

    User search(UserSearchFilter userSearchFilter);

}

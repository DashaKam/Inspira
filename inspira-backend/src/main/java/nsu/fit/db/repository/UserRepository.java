package nsu.fit.db.repository;

import nsu.fit.db.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    boolean existsByNickname(String nickname);

    UserEntity findByNickname(String nickname);

}

package nsu.fit.db.repository;

import nsu.fit.db.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    boolean existsByNickname(String nickname);

    UserEntity findByNickname(String nickname);

    UserEntity findById(int id);

    @Query("SELECT u FROM UserEntity u " +
            "WHERE u.id = :id OR u.nickname = :nickname")
    UserEntity findByIdOrNickname(@Param("id") Integer id, @Param("nickname") String nickname);
}

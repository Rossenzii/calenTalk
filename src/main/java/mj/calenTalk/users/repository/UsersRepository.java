package mj.calenTalk.users.repository;

import mj.calenTalk.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
    boolean existsByUserId(String userId);
}

package mj.calenTalk.users.repository;

import mj.calenTalk.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByGoogleId(String googleId);
    Optional<Users> findByEmail(String email);
}

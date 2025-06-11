package mj.calenTalk.chat.repository;

import mj.calenTalk.chat.entity.ChatRoom;
import mj.calenTalk.chat.entity.ChatRoomTalker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomTalkerRepository extends JpaRepository<ChatRoomTalker, Long> {

}

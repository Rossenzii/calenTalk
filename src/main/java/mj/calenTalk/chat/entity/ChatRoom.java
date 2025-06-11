package mj.calenTalk.chat.entity;

import jakarta.persistence.*;
import lombok.Builder;
import mj.calenTalk.users.entity.Users;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Table(name="chatroom")
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomId;
    private String name;
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatRoomTalker> talkers = new ArrayList<>();
}

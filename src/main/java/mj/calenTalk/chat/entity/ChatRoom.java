package mj.calenTalk.chat.entity;

import jakarta.persistence.*;
import lombok.Builder;
import mj.calenTalk.users.entity.Users;

@Entity
@Builder
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomId;
    private String name;
    @ManyToOne
    private Users users;
}

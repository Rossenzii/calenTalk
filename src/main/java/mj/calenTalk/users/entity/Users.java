package mj.calenTalk.users.entity;

import jakarta.persistence.*;
import lombok.*;
import mj.calenTalk.chat.entity.ChatRoomTalker;
import mj.calenTalk.users.enumerate.UsersType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="users_id")
    private Long id;
    private String password;
    private String googleId;
    private String name;
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false, length = 50)
    private UsersType userType;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatRoomTalker> chatRooms = new ArrayList<>();


}

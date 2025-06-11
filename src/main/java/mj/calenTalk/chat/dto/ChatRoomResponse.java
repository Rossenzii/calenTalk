package mj.calenTalk.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mj.calenTalk.chat.entity.ChatRoom;
import mj.calenTalk.users.entity.Users;

@Getter
@AllArgsConstructor
public class ChatRoomResponse {
    private Long id;
    private String roomId;
    private String fromUser;
    private String toUser;

    public static ChatRoomResponse from(ChatRoom chatRoom, Users fromUser, Users toUser) {
        return new ChatRoomResponse(
                chatRoom.getId(),
                chatRoom.getRoomId(),
                fromUser.getName(), // 또는 getNickname()
                toUser.getName()
        );
    }
}

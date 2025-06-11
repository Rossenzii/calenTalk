package mj.calenTalk.chat.dto;
import lombok.Data;
import lombok.Getter;

@Getter
public class CreateRoomRequest {
    private Long fromUserId;
    private Long toUserId;
}

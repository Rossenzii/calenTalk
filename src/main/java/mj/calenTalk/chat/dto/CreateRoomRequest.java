package mj.calenTalk.chat.dto;

import lombok.Data;

@Data
public class CreateRoomRequest {
    private String name;
    private Long userId;
}

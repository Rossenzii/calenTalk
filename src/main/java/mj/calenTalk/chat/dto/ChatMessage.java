package mj.calenTalk.chat.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class ChatMessage {

    private String roomId;
    private String sender;
    private String message;
    private LocalDateTime timestamp;
}

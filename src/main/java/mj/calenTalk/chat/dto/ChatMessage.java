package mj.calenTalk.chat.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    private String roomId;
    private String sender;
    private String message;
    private LocalDateTime timestamp;
}

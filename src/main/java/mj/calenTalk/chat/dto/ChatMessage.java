package mj.calenTalk.chat.dto;

import lombok.Data;

@Data
public class ChatMessage {
    private Long id;
    public enum MessageType {
        ENTER, TALK, EXIT, MATCH, MATCH_REQUEST;
    }
    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
}

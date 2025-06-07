package mj.calenTalk.chat.controller;
import lombok.RequiredArgsConstructor;
import mj.calenTalk.chat.dto.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    @MessageMapping("chat/message")
    public void message(ChatMessage message){
        messagingTemplate.convertAndSend("/sub/chat/room"+message.getRoomId(),message);
    }

}

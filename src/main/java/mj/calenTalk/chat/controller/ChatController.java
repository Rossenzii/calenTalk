package mj.calenTalk.chat.controller;
import lombok.RequiredArgsConstructor;
import mj.calenTalk.chat.dto.ChatMessage;
import mj.calenTalk.chat.service.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;
    @MessageMapping("/chat/message")
    public void message(ChatMessage message){
        chatService.saveMessage(message);
        messagingTemplate.convertAndSend("/sub/chat/room"+message.getRoomId(),message);
    }

}

package mj.calenTalk.chat.controller;

import lombok.RequiredArgsConstructor;
import mj.calenTalk.chat.dto.ChatMessage;
import mj.calenTalk.chat.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ChatHistoryController {

    private final ChatService chatService;

    @GetMapping("/chatroom/{roomId}/messages")
    public ResponseEntity<List<ChatMessage>> getMessages(@PathVariable String roomId) {
        List<ChatMessage> messages = chatService.getMessages(roomId);
        return ResponseEntity.ok(messages);
    }
}

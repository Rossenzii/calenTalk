package mj.calenTalk.chat.controller;

import lombok.RequiredArgsConstructor;
import mj.calenTalk.chat.dto.ChatMessage;
import mj.calenTalk.chat.dto.CreateRoomRequest;
import mj.calenTalk.chat.entity.ChatRoom;
import mj.calenTalk.chat.repository.ChatRoomRepository;
import mj.calenTalk.chat.service.ChatRoomService;
import mj.calenTalk.global.exception.ApplicationException;
import mj.calenTalk.global.exception.ErrorCode;
import mj.calenTalk.users.entity.Users;
import mj.calenTalk.users.repository.UsersRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping("/room")
    public ResponseEntity<ChatRoom> createChatRoom(@RequestBody CreateRoomRequest request) {
        ChatRoom room = chatRoomService.createRoom(request);
        return ResponseEntity.ok(room);
    }

}

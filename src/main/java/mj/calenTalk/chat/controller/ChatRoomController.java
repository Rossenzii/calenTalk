package mj.calenTalk.chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mj.calenTalk.chat.dto.ChatMessage;
import mj.calenTalk.chat.dto.ChatRoomResponse;
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
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    @PostMapping("/chatroom")
    public ResponseEntity<ChatRoomResponse> createChatRoom(@RequestBody CreateRoomRequest request) {
        log.info("✅ [Controller] createChatRoom 호출됨");
        log.info("➡️ fromUserId: {}, toUserId: {}", request.getFromUserId(), request.getToUserId());

        ChatRoomResponse roomResponse = chatRoomService.createRoom(request);
        return ResponseEntity.ok(roomResponse);
    }

}

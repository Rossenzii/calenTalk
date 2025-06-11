package mj.calenTalk.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mj.calenTalk.chat.dto.ChatRoomResponse;
import mj.calenTalk.chat.dto.CreateRoomRequest;
import mj.calenTalk.chat.entity.ChatRoom;
import mj.calenTalk.chat.entity.ChatRoomTalker;
import mj.calenTalk.chat.repository.ChatRoomRepository;
import mj.calenTalk.global.exception.ApplicationException;
import mj.calenTalk.global.exception.ErrorCode;
import mj.calenTalk.users.entity.Users;
import mj.calenTalk.users.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final UsersRepository usersRepository;
    private final ChatRoomRepository chatRoomRepository;

    public ChatRoomResponse createRoom(CreateRoomRequest request) {
        Users fromUser = getUserById(request.getFromUserId());
        Users toUser = getUserById(request.getToUserId());

        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .build();

        ChatRoomTalker talker1 = buildTalker(fromUser, chatRoom);
        ChatRoomTalker talker2 = buildTalker(toUser, chatRoom);

        chatRoom.getTalkers().add(talker1);
        chatRoom.getTalkers().add(talker2);

        ChatRoom saved = chatRoomRepository.save(chatRoom);
        return ChatRoomResponse.from(saved, fromUser, toUser);
    }
    private Users getUserById(Long id) {
        log.info("📌 [Service] getUserById 호출됨: id = {}", id);
        return usersRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ErrorCode.NOT_FOUND_EXCEPTION));
    }
    private ChatRoomTalker buildTalker(Users user, ChatRoom chatRoom) {
        return ChatRoomTalker.builder()
                .user(user)
                .chatRoom(chatRoom)
                .build();
    }


}

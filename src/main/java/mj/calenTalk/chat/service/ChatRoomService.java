package mj.calenTalk.chat.service;

import lombok.RequiredArgsConstructor;
import mj.calenTalk.chat.dto.CreateRoomRequest;
import mj.calenTalk.chat.entity.ChatRoom;
import mj.calenTalk.chat.repository.ChatRoomRepository;
import mj.calenTalk.global.exception.ApplicationException;
import mj.calenTalk.global.exception.ErrorCode;
import mj.calenTalk.users.entity.Users;
import mj.calenTalk.users.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final UsersRepository usersRepository;
    private final ChatRoomRepository chatRoomRepository;

    public ChatRoom createRoom(CreateRoomRequest request) {
        Users user = usersRepository.findById(request.getUserId())
                .orElseThrow(() -> new ApplicationException(ErrorCode.NOT_FOUND_EXCEPTION));
        ChatRoom room = ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .name(request.getName())
                .users(user)
                .build();

        return chatRoomRepository.save(room);
    }
}

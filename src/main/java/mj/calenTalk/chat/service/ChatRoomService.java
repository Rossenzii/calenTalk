package mj.calenTalk.chat.service;

import lombok.RequiredArgsConstructor;
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

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final UsersRepository usersRepository;
    private final ChatRoomRepository chatRoomRepository;

    public ChatRoom createRoom(CreateRoomRequest request) {
        Users fromUser = usersRepository.findById(request.getFromUserId())
                .orElseThrow(() -> new ApplicationException(ErrorCode.NOT_FOUND_EXCEPTION));
        Users toUser = usersRepository.findById(request.getToUserId())
                .orElseThrow(() -> new ApplicationException(ErrorCode.NOT_FOUND_EXCEPTION));
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .build();
        ChatRoomTalker talker1 = ChatRoomTalker.builder()
                .user(fromUser)
                .chatRoom(chatRoom)
                .build();

        ChatRoomTalker talker2 = ChatRoomTalker.builder()
                .user(toUser)
                .chatRoom(chatRoom)
                .build();

        chatRoom.getTalkers().add(talker1);
        chatRoom.getTalkers().add(talker2);

        return chatRoomRepository.save(chatRoom);
    }

}

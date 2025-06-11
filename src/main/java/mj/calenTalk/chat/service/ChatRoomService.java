package mj.calenTalk.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mj.calenTalk.chat.dto.ChatRoomResponse;
import mj.calenTalk.chat.dto.CreateRoomRequest;
import mj.calenTalk.chat.entity.ChatRoom;
import mj.calenTalk.chat.entity.ChatRoomTalker;
import mj.calenTalk.chat.repository.ChatRoomRepository;
import mj.calenTalk.chat.repository.ChatRoomTalkerRepository;
import mj.calenTalk.global.exception.ApplicationException;
import mj.calenTalk.global.exception.ErrorCode;
import mj.calenTalk.users.entity.Users;
import mj.calenTalk.users.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final UsersRepository usersRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomTalkerRepository talkerRepository;

    public ChatRoomResponse createRoom(CreateRoomRequest request) {
        Users fromUser = getUserById(request.getFromUserId());
        Users toUser = getUserById(request.getToUserId());

        Optional<ChatRoom> existingRoom = findExistingRoom(fromUser.getId(), toUser.getId());
        if (existingRoom.isPresent()) {
            return ChatRoomResponse.from(existingRoom.get(), fromUser, toUser);
        }

        ChatRoom chatRoom = buildChatRoom(fromUser, toUser);
        ChatRoom saved = chatRoomRepository.save(chatRoom);

        return ChatRoomResponse.from(saved, fromUser, toUser);
    }

    /**
     * 사용자 조회
     * @param userId
     * @return
     */
    private Users getUserById(Long userId) {
        return usersRepository.findById(userId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.NOT_FOUND_EXCEPTION));
    }

    /**
     * 기존 채팅방 확인
     * @param user1Id
     * @param user2Id
     * @return
     */
    private Optional<ChatRoom> findExistingRoom(Long user1Id, Long user2Id) {
        return chatRoomRepository.findByUserIds(user1Id, user2Id);
    }

    /**
     * 새 채팅방 생성
     * @param fromUser
     * @param toUser
     * @return
     */
    private ChatRoom buildChatRoom(Users fromUser, Users toUser) {
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .build();

        chatRoom.getTalkers().add(buildTalker(fromUser, chatRoom));
        chatRoom.getTalkers().add(buildTalker(toUser, chatRoom));

        return chatRoom;
    }

    /**
     * talker 앤티티 생성
     * @param user
     * @param chatRoom
     * @return
     */
    private ChatRoomTalker buildTalker(Users user, ChatRoom chatRoom) {
        return ChatRoomTalker.builder()
                .user(user)
                .chatRoom(chatRoom)
                .build();
    }
}

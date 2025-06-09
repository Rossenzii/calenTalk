package mj.calenTalk.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import mj.calenTalk.chat.dto.ChatMessage;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final RedisTemplate<String, String> redisTemplate;

    public void saveMessage(ChatMessage message) {
        String key = "chat:room:" + message.getRoomId();
        message.setTimestamp(LocalDateTime.now());
        redisTemplate.opsForList().rightPush(key, message.getMessage());
    }

    public List<String> getRecentMessages(String roomId, int count) {
        String key = "chat:room:" + roomId;
        return redisTemplate.opsForList().range(key, -count, -1);
    }
}

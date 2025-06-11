package mj.calenTalk.chat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import mj.calenTalk.chat.dto.ChatMessage;
import mj.calenTalk.global.exception.ApplicationException;
import mj.calenTalk.global.exception.ErrorCode;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public void saveMessage(ChatMessage message) {
        String key = "chat:room:" + message.getRoomId();
        message.setTimestamp(LocalDateTime.now());
        try {
            String jsonMessage = objectMapper.writeValueAsString(message);
            redisTemplate.opsForList().rightPush(key, jsonMessage);
        } catch (JsonProcessingException e) {
            throw new ApplicationException(ErrorCode.MESSAGE_PARSING_FAIL);
        }
    }

    public List<ChatMessage> getMessages(String roomId) {
        String key = "chat:room:" + roomId;
        List<String> jsonMessages = redisTemplate.opsForList().range(key, 0, -1);
        List<ChatMessage> messages = new ArrayList<>();
        for(String json:jsonMessages) {
            try {
                ChatMessage chatMessage = objectMapper.readValue(json, ChatMessage.class);
                messages.add(chatMessage);
            } catch (JsonProcessingException e) {
                throw new ApplicationException(ErrorCode.MESSAGE_PARSING_FAIL);
            }
        }
        return messages;
    }
}

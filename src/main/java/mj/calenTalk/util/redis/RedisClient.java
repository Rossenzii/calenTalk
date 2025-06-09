package mj.calenTalk.util.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisClient {
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * redis에 값 삽입
     * @param key
     * @param value
     * @param timeout - 유효 시간
     */
    public void setValue(String key, String value, Long timeout) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        values.set(key, value, timeout, TimeUnit.MILLISECONDS);
    }

    public String getValue(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        return value != null ? value.toString() : null;
    }

    public void deleteValue(String key) {
        redisTemplate.delete(key);
    }
}

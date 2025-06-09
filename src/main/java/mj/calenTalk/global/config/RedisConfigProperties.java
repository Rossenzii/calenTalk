package mj.calenTalk.global.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

/**
 * 설정 정보 자바로 가져옴
 */
@ConfigurationProperties(prefix = "spring.redis")
public record RedisConfigProperties(
        String host,
        int port,
        String password
) {
}
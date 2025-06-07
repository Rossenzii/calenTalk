package mj.calenTalk.global.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

//@ConstructorBinding
@ConfigurationProperties(prefix = "spring.redis")
public record RedisConfigProperties(
        String host,
        int port,
        String password
) {
}
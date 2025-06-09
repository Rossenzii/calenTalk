package mj.calenTalk.global.config;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@AllArgsConstructor
@EnableConfigurationProperties({RedisConfigProperties.class})
@EnableRedisRepositories
public class RedisConfig {

    private final RedisConfigProperties redisConfigProperties;

    /**
     * redis 접속에 필요한 설정
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisConfigProperties.host(), redisConfigProperties.port());
        redisStandaloneConfiguration.setPassword(RedisPassword.of(redisConfigProperties.password()));
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    /**
     * redis에 값을 저장 & 불러옴
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        // redisTemplate.setEnableTransactionSupport(true);
        return redisTemplate;
    }
}


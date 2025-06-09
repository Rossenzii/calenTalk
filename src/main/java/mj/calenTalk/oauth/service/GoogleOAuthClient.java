package mj.calenTalk.oauth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import mj.calenTalk.global.security.jwt.JwtProvider;
import mj.calenTalk.oauth.dto.TokenDto;
import mj.calenTalk.oauth.dto.UserInfoDto;
import mj.calenTalk.users.entity.Users;
import mj.calenTalk.util.redis.RedisClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GoogleOAuthClient {
    @Value("${GOOGLE_CLIENT_ID}")
    private String clientId;
    @Value("${GOOGLE_CLIENT_SECRET}")
    private String clientSecret;
    @Value("${REDIRECT_URI}")
    private String redirectUri;

    private final JwtProvider jwtProvider;
    private final RedisClient redisClient;

    public TokenDto getAccessToken(String code){
        String tokenUrl = "https://oauth2.googleapis.com/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        WebClient webClient = WebClient.create();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        String response = webClient.post()
                .uri(tokenUrl)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .body(BodyInserters.fromFormData(params))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response, TokenDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public UserInfoDto getUserInfo(String accessToken){
        String userInfoUrl = "https://www.googleapis.com/oauth2/v2/userinfo";
        WebClient webClient = WebClient.create(userInfoUrl);

        String response = webClient.get()
                .uri(userInfoUrl)
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper objectMapper = new ObjectMapper();
        UserInfoDto googleUserInfo;
        try {
            googleUserInfo = objectMapper.readValue(response, UserInfoDto.class);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return googleUserInfo;

    }
    /**
     * cookie 저장, redis 저장
     * @param users
     * @param response
     */
    public Map<String, String> signIn(Users users, HttpServletResponse response){
        String refreshToken = jwtProvider.generateRefreshToken(users);
        String accessToken = jwtProvider.generateAccessToken(users);

        redisClient.setValue(users.getEmail(), refreshToken, 30 * 24 * 60 * 60 * 1000L); //30days
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(Duration.ofDays(7))
                .build();
        response.addHeader("Set-Cookie", cookie.toString());

        Map<String, String> tokenResponse = new HashMap<>();
        tokenResponse.put("accessToken", accessToken);
        return tokenResponse;
    }
}


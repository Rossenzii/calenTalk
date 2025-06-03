package mj.calenTalk.oauth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import mj.calenTalk.global.exception.ApplicationException;
import mj.calenTalk.global.exception.ErrorCode;
import mj.calenTalk.oauth.dto.response.GoogleToken;
import mj.calenTalk.oauth.dto.response.GoogleUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Value("${GOOGLE_CLIENT_ID}")
    private String clientId;
    @Value("${GOOGLE_CLIENT_SECRET}")
    private String clientSecret;
    @Value("${REDIRECT_URI}")
    private String redirectUri;

    public GoogleToken getAccessToken(String code){
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
            return objectMapper.readValue(response, GoogleToken.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public GoogleUserInfo getUserInfo(String accessToken){
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
        GoogleUserInfo googleUserInfo;
        try {
            googleUserInfo = objectMapper.readValue(response, GoogleUserInfo.class);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return googleUserInfo;

    }
}


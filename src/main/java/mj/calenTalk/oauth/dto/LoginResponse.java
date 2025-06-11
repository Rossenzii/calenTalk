package mj.calenTalk.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mj.calenTalk.users.entity.Users;

import java.util.Map;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    private Long userId;
    private String name;
    public static LoginResponse from(Map<String, String> tokenMap, Users users) {
        return new LoginResponse(
                tokenMap.get("accessToken"),
                users.getId(),
                users.getName()
        );
    }
}


package mj.calenTalk.oauth.dto;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDto {
    String access_token;
    @Nullable
    String refresh_token;
    int expires_in;
    String token_type;
    private String scope;
    String id_token;

}

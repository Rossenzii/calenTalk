package mj.calenTalk.oauth.dto.response;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoogleToken {
    String access_token;
    @Nullable
    String refresh_token;
    int expires_in;
    String token_type;
    private String scope;
    String id_token;

}

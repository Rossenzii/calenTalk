package mj.calenTalk.users.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupDto {
    private String userId;
    private String nickname;
    private String password;
    private String email;
}

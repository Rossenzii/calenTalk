package mj.calenTalk.oauth.dto.response;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleUserInfo {
    String id;
    String email;
    String name;
    String picture;
    String locale;
}

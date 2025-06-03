package mj.calenTalk.oauth.controller;

import lombok.RequiredArgsConstructor;
import mj.calenTalk.oauth.dto.response.GoogleToken;
import mj.calenTalk.oauth.dto.response.GoogleUserInfo;
import mj.calenTalk.oauth.service.AuthService;
import mj.calenTalk.users.service.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/oauth")
public class AuthController {
    private final AuthService authService;
    private final UsersService userService;
    @PostMapping("/google")
    public ResponseEntity<?> userLogin(@RequestBody Map<String, String> body) {
        String code = body.get("code");
        GoogleToken accessToken = authService.getAccessToken(code);
        GoogleUserInfo userInfo = authService.getUserInfo(accessToken.getAccess_token());
        userService.saveOrUpdateUser(userInfo);
        return ResponseEntity.ok(userInfo);

    }
}

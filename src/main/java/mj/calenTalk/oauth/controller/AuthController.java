package mj.calenTalk.oauth.controller;

import lombok.RequiredArgsConstructor;
import mj.calenTalk.global.security.jwt.JwtProvider;
import mj.calenTalk.oauth.dto.TokenDto;
import mj.calenTalk.oauth.dto.UserInfoDto;
import mj.calenTalk.oauth.service.AuthService;
import mj.calenTalk.users.entity.Users;
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
    private final JwtProvider jwtProvider;
    @PostMapping("/google")
    public ResponseEntity<?> userLogin(@RequestBody Map<String, String> body) {
        String code = body.get("code");
        TokenDto dto = authService.getAccessToken(code);
        UserInfoDto userInfo = authService.getUserInfo(dto.getAccess_token());
        Users users = userService.saveOrUpdateUser(userInfo);

        jwtProvider.generateAccessToken(users);
        jwtProvider.generateRefreshToken(users);
        return ResponseEntity.ok(userInfo);

    }
}

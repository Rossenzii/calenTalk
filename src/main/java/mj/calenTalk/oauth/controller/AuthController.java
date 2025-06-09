package mj.calenTalk.oauth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import mj.calenTalk.global.security.PrincipalDetails;
import mj.calenTalk.global.security.jwt.JwtProvider;
import mj.calenTalk.oauth.dto.TokenDto;
import mj.calenTalk.oauth.dto.UserInfoDto;
import mj.calenTalk.oauth.service.GoogleOAuthClient;
import mj.calenTalk.users.entity.Users;
import mj.calenTalk.users.service.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/oauth")
public class AuthController {
    private final GoogleOAuthClient googleOAuthClient;
    private final UsersService userService;
    private final JwtProvider jwtProvider;
    @PostMapping("/google")
    public ResponseEntity<?> userLogin(@RequestBody Map<String, String> body, HttpServletResponse response) {
        String code = body.get("code");
        TokenDto dto = googleOAuthClient.getAccessToken(code);
        UserInfoDto userInfo = googleOAuthClient.getUserInfo(dto.getAccess_token());
        Users users = userService.saveOrUpdateUser(userInfo);

        Map<String, String> tokenMap = googleOAuthClient.signIn(users, response);
        return ResponseEntity.ok(tokenMap);
    }

//    @GetMapping("/reissue")
//    public void reissue(HttpServletRequest request, @AuthenticationPrincipal PrincipalDetails principalDetails){
//        String token = request.getHeader("Authorization");
//
//    }

}

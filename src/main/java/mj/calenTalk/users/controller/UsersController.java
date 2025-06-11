package mj.calenTalk.users.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mj.calenTalk.global.security.PrincipalDetails;
import mj.calenTalk.users.dto.UserDto;
import mj.calenTalk.users.service.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UsersController {
    private final UsersService usersService;
    @GetMapping("/userlist")
    public ResponseEntity<List<UserDto>> getAllUsersNotme(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ResponseEntity.ok(usersService.getAllExcept(principalDetails.getEmail()));
    }

}

package mj.calenTalk.users.controller;

import lombok.RequiredArgsConstructor;
import mj.calenTalk.users.dto.request.SignupDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UsersController {

    @PostMapping("/success")
    public ResponseEntity<String> userLogin(@RequestBody SignupDto dto) {
        return ResponseEntity.ok("success");
    }
}

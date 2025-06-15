package mj.calenTalk.calendar.controller;

import lombok.RequiredArgsConstructor;
import mj.calenTalk.calendar.service.CalendarService;
import mj.calenTalk.global.security.PrincipalDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class CalendarController {

}

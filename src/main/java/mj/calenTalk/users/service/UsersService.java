package mj.calenTalk.users.service;

import lombok.RequiredArgsConstructor;
import mj.calenTalk.oauth.dto.response.GoogleUserInfo;
import mj.calenTalk.users.entity.Users;
import mj.calenTalk.users.repository.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    @Transactional
    public Users saveOrUpdateUser(GoogleUserInfo userInfo) {
        return usersRepository.findByGoogleId(userInfo.getId())
                .orElseGet(() -> {
                    Users newUser = Users.builder()
                            .googleId(userInfo.getId())
                            .name(userInfo.getName())
                            .email(userInfo.getEmail())
                            .createdAt(LocalDateTime.now())
                            .build();
                    return usersRepository.save(newUser);});
    }

}

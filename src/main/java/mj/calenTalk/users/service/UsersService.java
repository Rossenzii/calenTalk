package mj.calenTalk.users.service;

import lombok.RequiredArgsConstructor;
import mj.calenTalk.oauth.dto.UserInfoDto;
import mj.calenTalk.users.entity.Users;
import mj.calenTalk.users.enumerate.UsersType;
import mj.calenTalk.users.repository.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    @Transactional
    public Users saveOrUpdateUser(UserInfoDto userInfo) {
        Users users = usersRepository.findByGoogleId(userInfo.getId())
                .orElseThrow(() -> new RuntimeException("해당 사용자가 존재하지 않습니다."));
        if (users == null) {
            Users newUser = Users.builder()
                    .googleId(userInfo.getId())
                    .name(userInfo.getName())
                    .email(userInfo.getEmail())
                    .createdAt(LocalDateTime.now())
                    .userType(UsersType.GENERAL)
                    .build();
            return usersRepository.save(newUser);
        }
        return users;
    }

}

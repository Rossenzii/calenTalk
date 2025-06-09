package mj.calenTalk.users.service;

import lombok.RequiredArgsConstructor;
import mj.calenTalk.oauth.dto.UserInfoDto;
import mj.calenTalk.users.dto.UserDto;
import mj.calenTalk.users.entity.Users;
import mj.calenTalk.users.enumerate.UsersType;
import mj.calenTalk.users.repository.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    @Transactional
    public Users saveOrUpdateUser(UserInfoDto userInfo) {
        return usersRepository.findByEmail(userInfo.getEmail())
                .orElseGet(()->{
                    Users newUser = Users.builder()
                            .googleId(userInfo.getId())
                            .name(userInfo.getName())
                            .email(userInfo.getEmail())
                            .createdAt(LocalDateTime.now())
                            .userType(UsersType.GENERAL)
                            .build();
                    return usersRepository.save(newUser);
                });
    }
    public List<UserDto> getAllExcept(String email) {
        return usersRepository.findByEmailNot(email).stream()
                .map(UserDto::fromEntity)
                .collect(Collectors.toList());
    }


}

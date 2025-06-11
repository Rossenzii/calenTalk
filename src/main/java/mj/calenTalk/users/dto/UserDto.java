package mj.calenTalk.users.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mj.calenTalk.users.entity.Users;

@Getter
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;

    public static UserDto fromEntity(Users user) {
        return new UserDto(user.getId(), user.getName());
    }
}


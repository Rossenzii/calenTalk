package mj.calenTalk.users.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mj.calenTalk.users.entity.Users;

@Getter
@AllArgsConstructor
public class UserDto {
    public String name;
    public static UserDto fromEntity(Users user) {
        return new UserDto(user.getName());
    }
}

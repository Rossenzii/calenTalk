package mj.calenTalk.global.security;

import lombok.RequiredArgsConstructor;
import mj.calenTalk.users.entity.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class PrincipalDetails implements UserDetails {
    private final Users users;
    //권한 정보
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(users.getUserType().getRole()));
        return authorities;
    }

    // 사용자의 비밀번호 반환
    @Override
    public String getPassword() {
        return users.getPassword();
    }

    // 사용자의 이름 반환
    @Override
    public String getUsername() {
        return users.getName();
    }

}

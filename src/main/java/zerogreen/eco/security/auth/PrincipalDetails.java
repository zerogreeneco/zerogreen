package zerogreen.eco.security.auth;

/*
* 시큐리티가 /login을 주소 요청을 낚아서 로그인 진행
* 로그인 진행이 완료가 되면 시큐리티가 가진 session을 만든다. (Security ContextHolder)
* Authentication 타입의 객체만 가능
* Authentication 내부에 Member 정보가 있어햐 한다.
* */

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import zerogreen.eco.entity.userentity.BasicUser;

import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetails implements UserDetails {

    private BasicUser user; // 컴포지션

    public PrincipalDetails(BasicUser user) {
        this.user = user;
    }

    // 해당 Member의 권한 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();

        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return String.valueOf(user.getUserRole());
            }
        });

        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

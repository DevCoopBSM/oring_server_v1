package bsm.devcoop.oring.entity.account.user;

import bsm.devcoop.oring.entity.account.user.types.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.Collections;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final User user;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final String initialUserPassword = "123456";
    private final String initialUserPin = "1234";

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRoles().name()));
    }

    // Custom 사용자 정보 메서드
    
    public String getUserCode() {
        return user.getUserCode();
    }
    
    public String getUserEmail() {
        return user.getUserEmail();
    }
    
    public Role getRole() {
        return user.getRoles();
    }
    
    public int getUserPoint() {
        return user.getUserPoint();
    }
    
    public boolean isInitialUserPassword() { // 초기 비밀번호인가?
        return bCryptPasswordEncoder.matches(initialUserPassword, user.getUserPassword());
    }

    public boolean isInitialUserPin() { // 초기 핀 번호인가?
        return bCryptPasswordEncoder.matches(initialUserPin, user.getUserPin());
    }
    
    
    // UserDetails 인터페이스 구현 메서드
    @Override
    public String getPassword() {
        return user.getUserPassword();
    }


    @Override
    public String getUsername() {
        return user.getUserName();
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

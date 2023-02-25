package cn.jpanda.demo.manager.vo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
public class LoginUser extends User {

    private LoginUser(UserDetails user) {
        super(user.getUsername(), user.getPassword(), user.isEnabled(), user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isAccountNonLocked(), user.getAuthorities());
    }

    public static LoginUser of(UserDetails userDetails) {
        return new LoginUser(userDetails);
    }

    public static class LoginUserBuilder {
        // 扩展其余用户数据
        // 扩展
        private UserDetails userDetails;

        private LoginUserBuilder(UserDetails userDetails) {
            this.userDetails = userDetails;
        }

        public static LoginUserBuilder with(UserDetails userDetails) {

            return new LoginUserBuilder(userDetails);
        }

        public LoginUser build() {
            return LoginUser.of(this.userDetails);
        }
    }
}

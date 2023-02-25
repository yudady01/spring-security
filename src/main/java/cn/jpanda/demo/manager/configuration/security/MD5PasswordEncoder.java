package cn.jpanda.demo.manager.configuration.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

/**
 * 自定义MD5加密策略，兼容旧系统的加密方式
 */
public class MD5PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {

        return encodeWithMd5(String.valueOf(charSequence));
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equalsIgnoreCase(encodeWithMd5(String.valueOf(charSequence)));
    }

    private String encodeWithMd5(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }
}

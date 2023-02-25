package cn.jpanda.demo.manager.configuration.security;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MD5PasswordEncoderTest {
    private static PasswordEncoder passwordEncoder;

    private static final String CIPHER_TEXT = "96e79218965eb72c92a549dd5a330112";
    private static final String RAW_TEXT = "111111";

    @BeforeClass
    public static void init() {
        passwordEncoder = new MD5PasswordEncoder();
    }

    @Test
    public void encode() {
        assert CIPHER_TEXT.equalsIgnoreCase(passwordEncoder.encode(RAW_TEXT));
    }

    @Test
    public void matches() {
        assert passwordEncoder.matches(RAW_TEXT, CIPHER_TEXT);
    }
}
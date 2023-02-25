package cn.jpanda.demo.manager.configuration.security;

import cn.jpanda.demo.manager.DemoManagerMockMvcBaseTest;
import cn.jpanda.demo.manager.vo.LoginUser;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;

import javax.annotation.Resource;
import javax.servlet.Filter;

@Slf4j
public class JPandaUserDetailsServiceTest extends DemoManagerMockMvcBaseTest {
    /**
     * 用户名
     */
    public static final String USERNAME = "admin";
    /**
     * 触发异常
     */
    public static final String USERNAME_FOR_ERROR = "ADMIN_ERROR";
    /**
     * 用户密码
     */
    public static final String PASSWORD = "admin";
    /**
     * 用户角色(授权)
     */
    public static final String MOCK_ROLE = "ADMIN";

    @SpyBean
    private JPandaUserDetailsService jPandaUserDetailsService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Filter springSecurityFilterChain;

    /**
     * 提供给子类复写的MockMvc构建器的扩展方法
     *
     * @param builder MockMvc构建器
     * @return MockMvc构建器
     */
    @Override
    protected DefaultMockMvcBuilder doHandlerMockMvcBuilder(DefaultMockMvcBuilder builder) {
        return builder.addFilters(springSecurityFilterChain);
    }

    @Before
    public void init() {
        // 调用父类方法初始化MockMVC
        super.init();
        // 返回模拟用户数据
        Mockito.doReturn(LoginUser
                .of(User.builder()
                        .username(USERNAME)
                        .password(passwordEncoder.encode(PASSWORD))
                        .authorities(MOCK_ROLE)
                        .build()))
                .when(jPandaUserDetailsService).loadUserByUsername(USERNAME)
        ;

        Mockito.doReturn(null)
                .when(jPandaUserDetailsService).loadUserByUsername(USERNAME_FOR_ERROR)
        ;
    }

    @Test
    @SneakyThrows
    public void loadUserByUsernameWithForm() {
        JSONUsernamePasswordAuthenticationFilter.User user = new JSONUsernamePasswordAuthenticationFilter.User();
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        log.info(success((mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", USERNAME)
                .param("password", PASSWORD)
        ))).andReturn().getResponse().getContentAsString());

    }

    @Test
    @SneakyThrows
    public void loadUserByUsernameWithJSON() {
        JSONUsernamePasswordAuthenticationFilter.User user = new JSONUsernamePasswordAuthenticationFilter.User();
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);

        assertSuccess(mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(toJson(user))
        ));
    }

    @Test
    @SneakyThrows
    public void loadUserByUsernameWithError() {
        JSONUsernamePasswordAuthenticationFilter.User user = new JSONUsernamePasswordAuthenticationFilter.User();
        user.setUsername(USERNAME_FOR_ERROR);
        user.setPassword(PASSWORD);

        assertFail(mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(toJson(user))
        ));
    }
}
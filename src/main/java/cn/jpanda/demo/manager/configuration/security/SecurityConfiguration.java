package cn.jpanda.demo.manager.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 安全访问控制
 */
@Configuration
@Import(JPandaUserDetailsService.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter implements InitializingBean {

    private List<SecurityConfigurationExtends> securityConfigurationExtendsList;

    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private JPandaAuthenticationSuccessHandler jPandaAuthenticationSuccessHandler;
    @Resource
    private JPandaAuthenticationFailureHandler jPandaAuthenticationFailureHandler;

    @Resource
    private JSONAuthenticationEntryPoint jsonAuthenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling().authenticationEntryPoint(jsonAuthenticationEntryPoint)/* 将权限校验错误转换为JSON*/
                .and()
                .addFilterAt(jsonUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)/* 支持JSON格式的登录请求*/
                .authorizeRequests()  //定义
                .antMatchers(new String[]{
                        "/plugins/**"
                        , "/js/**"
                        , "/css/**"
                        , "/front/**"
                        , "/components/**"
                        , "/**/favicon.ico"
                }).permitAll() /*静态资源不受保护*/
                .anyRequest().authenticated() /*其余请求，均需验权*/
                .and()
                .sessionManagement().maximumSessions(1) /*会话管理*/
                .and()
                .and()
                .logout()
                .logoutUrl("/logout")
                .and()
                .formLogin()
                .loginPage("/login.html")  //定义当需要用户登录时候，转到的登录页面
                .loginProcessingUrl("/user/login")  // 自定义的登录接口
                .permitAll()
                .defaultSuccessUrl("/index.html").permitAll()
                .and()
                .logout()
                .permitAll()
                // 自动登录
                .and()
                .rememberMe()
                .and()
                .csrf().disable()
        ;
        //解决中文乱码问题
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter, CsrfFilter.class);

        // 执行扩展
        executeExtend(http);
    }

    private void executeExtend(HttpSecurity http) {
        // 扩展
        securityConfigurationExtendsList.forEach((s) -> {
            try {
                s.configure(http);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void configure(WebSecurity web) {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/css/**");
        web.ignoring().antMatchers("/js/**");
        web.ignoring().antMatchers("/images/**");
        web.ignoring().antMatchers("/lib/**");
        web.ignoring().antMatchers("/fonts/**");
        web.ignoring().antMatchers("/lang/**");
        web.ignoring().antMatchers("/login/**");
        web.ignoring().antMatchers("/login.html");
        //解决服务注册url被拦截的问题
        web.ignoring().antMatchers("/swagger-resources/**");
        web.ignoring().antMatchers("/v2/**");
        web.ignoring().antMatchers("/**/*.json");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void afterPropertiesSet() {
        // 加载安全扩展配置
        ApplicationContext applicationContext = getApplicationContext();

        String[] names = applicationContext.getBeanNamesForType(SecurityConfigurationExtends.class);
        if (names == null) {
            securityConfigurationExtendsList = Collections.emptyList();
            return;
        }

        securityConfigurationExtendsList = new ArrayList<>(names.length);
        for (String name : names) {
            securityConfigurationExtendsList.add(applicationContext.getBean(name, SecurityConfigurationExtends.class));
        }
    }

    public JSONUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter() throws Exception {
        JSONUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter = new JSONUsernamePasswordAuthenticationFilter(objectMapper);
        jsonUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(jPandaAuthenticationSuccessHandler);
        jsonUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(jPandaAuthenticationFailureHandler);
        jsonUsernamePasswordAuthenticationFilter.setFilterProcessesUrl("/user/login");
        jsonUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManager());
        return jsonUsernamePasswordAuthenticationFilter;
    }

    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}

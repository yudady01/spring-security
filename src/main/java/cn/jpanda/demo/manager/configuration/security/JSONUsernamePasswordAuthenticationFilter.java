package cn.jpanda.demo.manager.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * 支持JSON登录的用户名密码过滤器
 */
public class JSONUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private ObjectMapper objectMapper;

    public JSONUsernamePasswordAuthenticationFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    protected List<HttpMethod> SUPPORT_METHODS = Collections.singletonList(HttpMethod.POST);

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        if (!MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(request.getContentType())
                && !MediaType.APPLICATION_JSON_UTF8_VALUE.equalsIgnoreCase(request.getContentType())) {
            return super.attemptAuthentication(request, response);
        }

        // 方法校验
        HttpMethod method = HttpMethod.resolve(request.getMethod());
        if (null == method || !SUPPORT_METHODS.contains(method)) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        String username;
        String password;
        User user = readUser(request);
        if (user == null) {
            throw new AuthenticationServiceException("无用户登录数据: " + request.getContentType());
        }
        username = user.getUsername();
        password = user.getPassword();

        if (username == null) {
            username = "";
        }
        if (password == null) {
            password = "";
        }

        username = username.trim();
        password = password.trim();
        // 解析request内容
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        this.setDetails(request, authenticationToken);
        return this.getAuthenticationManager().authenticate(authenticationToken);
    }


    @Override
    protected String obtainUsername(HttpServletRequest request) {
        return super.obtainUsername(request);
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        return super.obtainPassword(request);
    }

    private User readUser(HttpServletRequest request) {
        try {
            return objectMapper.readValue(request.getInputStream(), User.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Data
    public static class User {
        private String username;
        private String password;
    }
}

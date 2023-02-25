package cn.jpanda.demo.manager.configuration.security;

import cn.jpanda.demo.manager.configuration.result.Result;
import cn.jpanda.demo.manager.configuration.result.UniversalResultHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JPandaAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Resource
    private ObjectMapper objectMapper;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // 获取需要返回的数据，执行包装
        Object o = authentication.getPrincipal();
        Result<Object> result = UniversalResultHelper.success(o);
        response.setStatus(HttpStatus.OK.value());

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().append(write2Str(result));
    }

    public String write2Str(Result r) {
        try {
            return objectMapper.writeValueAsString(r);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}

package cn.jpanda.demo.manager.configuration.security;

import cn.jpanda.demo.manager.configuration.exception.BusinessRuntimeException;
import cn.jpanda.demo.manager.configuration.exception.BusinessRuntimeExceptionHandler;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JSONAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Resource
    private BusinessRuntimeExceptionHandler businessRuntimeExceptionHandler;

    @Resource
    private BusinessRuntimeExceptionHandler.BusinessRuntimeExceptionHandlerCallBack callBack;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        if (!isAjax(httpServletRequest)) {
        // 重定向到403错误页面
            httpServletRequest.getRequestDispatcher("/").forward(httpServletRequest, httpServletResponse);
            return;
        }
        // 获取需要返回的数据，执行包装
        businessRuntimeExceptionHandler.buildResult(httpServletRequest
                , httpServletResponse
                , wrapper(e)
                , callBack);
    }

    public BusinessRuntimeException wrapper(AuthenticationException authenticationException) {
        if (authenticationException instanceof InsufficientAuthenticationException) {
            return BusinessRuntimeException.exception(AuthenticationECode.NO_AUTHENTICATION, authenticationException);
        }

        return BusinessRuntimeException.exception(AuthenticationECode.NEED_LOGIN, authenticationException);
    }

    protected boolean isAjax(HttpServletRequest request) {

        return (request.getHeader("X-Requested-With") != null &&
                "XMLHttpRequest".equals(request.getHeader("X-Requested-With").toString()));
    }
}

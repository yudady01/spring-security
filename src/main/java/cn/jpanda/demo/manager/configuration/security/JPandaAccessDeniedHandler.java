package cn.jpanda.demo.manager.configuration.security;

import cn.jpanda.demo.manager.configuration.exception.BusinessRuntimeException;
import cn.jpanda.demo.manager.configuration.exception.BusinessRuntimeExceptionHandler;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 默认无授权处理
 */
@Component
public class JPandaAccessDeniedHandler implements AccessDeniedHandler {
    @Resource
    private BusinessRuntimeExceptionHandler businessRuntimeExceptionHandler;

    @Resource
    protected BusinessRuntimeExceptionHandler.BusinessRuntimeExceptionHandlerCallBack businessRuntimeExceptionHandlerCallBack;

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        if (!isAjax(httpServletRequest)) {
            // 重定向到403错误页面
            httpServletRequest.getRequestDispatcher("error/403").forward(httpServletRequest, httpServletResponse);
            return;
        }
        // 处理Ajax异常
        BusinessRuntimeException businessRuntimeException = BusinessRuntimeException.exception(AuthenticationECode.NO_AUTHENTICATION);
        businessRuntimeExceptionHandler.buildResult(httpServletRequest, httpServletResponse, businessRuntimeException, businessRuntimeExceptionHandlerCallBack);
    }

    protected boolean isAjax(HttpServletRequest request) {

        return (request.getHeader("X-Requested-With") != null &&
                "XMLHttpRequest".equals(request.getHeader("X-Requested-With").toString()));
    }
}

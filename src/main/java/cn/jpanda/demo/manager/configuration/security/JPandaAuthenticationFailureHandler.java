package cn.jpanda.demo.manager.configuration.security;

import cn.jpanda.demo.manager.configuration.exception.BusinessRuntimeException;
import cn.jpanda.demo.manager.configuration.exception.BusinessRuntimeExceptionHandler;
import cn.jpanda.demo.manager.configuration.exception.ecodes.GlobalECode;
import cn.jpanda.demo.manager.enums.ecode.UserECode;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JPandaAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Resource
    private BusinessRuntimeExceptionHandler businessRuntimeExceptionHandler;

    @Resource
    protected BusinessRuntimeExceptionHandler.BusinessRuntimeExceptionHandlerCallBack businessRuntimeExceptionHandlerCallBack;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        BusinessRuntimeException businessRuntimeException = null;
        // 处理自定义业务异常
        if (exception instanceof BadCredentialsException) {
            businessRuntimeException = BusinessRuntimeException.exception(UserECode.USER_NAME_OR_PASSWORD_ERROR);
        }

        if (exception instanceof InternalAuthenticationServiceException) {
            // UserDetailsService接口理论上是不允许返回NULL的。
            businessRuntimeException = BusinessRuntimeException.exception(GlobalECode.CODING_IS_NOT_STANDARDIZED, exception, exception.getMessage());
        }

        if (businessRuntimeException == null) {
            // 处理子异常
            if (null != exception.getCause()) {
                if (exception.getCause() instanceof BusinessRuntimeException) {
                    businessRuntimeException = (BusinessRuntimeException) exception.getCause();
                }
            }
        }

        // 通用异常包装
        if (businessRuntimeException == null) {
            businessRuntimeException = BusinessRuntimeException.exception(GlobalECode.UNKNOWN_EXCEPTION_WITH_MESSAGE, exception, exception.getMessage());
        }
        // 处理其他异常
        businessRuntimeExceptionHandler.buildResult(request, response, businessRuntimeException, businessRuntimeExceptionHandlerCallBack);
    }
}

package cn.jpanda.demo.manager.configuration.exception;

import cn.jpanda.demo.manager.configuration.result.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 业务异常处理器
 */
public interface BusinessRuntimeExceptionHandler {

    Result buildResult(HttpServletRequest request, HttpServletResponse response, BusinessRuntimeException exception);

    default void buildResult(HttpServletRequest request, HttpServletResponse response, BusinessRuntimeException exception, BusinessRuntimeExceptionHandlerCallBack callBack) {
        callBack.callback(request, response, exception, buildResult(request, response, exception));
    }

    @FunctionalInterface
    interface BusinessRuntimeExceptionHandlerCallBack {
        void callback(HttpServletRequest request, HttpServletResponse response, BusinessRuntimeException exception, Result result);
    }
}

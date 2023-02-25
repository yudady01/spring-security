package cn.jpanda.demo.manager.configuration.log.handler;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author HanQi [Jpanda@aliyun.com]
 * @version 1.0
 * @since 2020/6/8 16:34:58
 */
public interface AccessLogProceedingJoinPointHandler {
    Object handler(MethodInvocation point) throws Throwable;
}

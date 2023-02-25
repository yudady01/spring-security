package cn.jpanda.demo.manager.configuration.log.visitors;

import cn.jpanda.demo.manager.configuration.log.handler.AccessLogProceedingJoinPointHandler;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 同意拦截日志
 *
 * @author HanQi [Jpanda@aliyun.com]
 * @version 1.0
 * @since 2020/6/9 9:37:10
 */
public class AnnotationAspectPointCutAdvisor extends StaticMethodMatcherPointcutAdvisor {
    AccessLogProceedingJoinPointHandler handler;
    private Class<? extends Annotation> interceptionAnnotation;


    public AnnotationAspectPointCutAdvisor(Class<? extends Annotation> interceptionAnnotation, AccessLogProceedingJoinPointHandler handler) {
        this.interceptionAnnotation = interceptionAnnotation;
        this.handler = handler;
        setAdvice((MethodInterceptor) handler::handler);
    }

    @Override
    public boolean matches(Method method, Class<?> aClass) {
        return AnnotationUtils.findAnnotation(method, interceptionAnnotation) != null;
    }
}

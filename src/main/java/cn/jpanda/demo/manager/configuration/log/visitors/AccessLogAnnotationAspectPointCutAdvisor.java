package cn.jpanda.demo.manager.configuration.log.visitors;


import cn.jpanda.demo.manager.configuration.log.annotations.AccessLog;
import cn.jpanda.demo.manager.configuration.log.handler.AccessLogProceedingJoinPointHandler;

/**
 * @author HanQi [Jpanda@aliyun.com]
 * @version 1.0
 * @since 2020/6/9 10:04:31
 */
public class AccessLogAnnotationAspectPointCutAdvisor extends AnnotationAspectPointCutAdvisor {
    public AccessLogAnnotationAspectPointCutAdvisor(AccessLogProceedingJoinPointHandler handler) {
        super(AccessLog.class, handler);
    }
}

package cn.jpanda.demo.manager.configuration.log.visitors;

import cn.jpanda.demo.manager.configuration.log.handler.AccessLogProceedingJoinPointHandler;
import org.springframework.web.bind.annotation.Mapping;

/**
 * @author HanQi [Jpanda@aliyun.com]
 * @version 1.0
 * @since 2020/6/9 10:04:04
 */
public class MappingAnnotationAspectPointCutAdvisor extends AnnotationAspectPointCutAdvisor {

    public MappingAnnotationAspectPointCutAdvisor(AccessLogProceedingJoinPointHandler handler) {
        super(Mapping.class, handler);
    }
}

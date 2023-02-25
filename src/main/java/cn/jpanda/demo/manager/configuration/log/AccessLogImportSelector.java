package cn.jpanda.demo.manager.configuration.log;

import cn.jpanda.demo.manager.configuration.log.annotations.EnableAccessLog;
import cn.jpanda.demo.manager.configuration.log.visitors.AccessLogAnnotationAspectPointCutAdvisor;
import cn.jpanda.demo.manager.configuration.log.visitors.MappingAnnotationAspectPointCutAdvisor;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author HanQi [Jpanda@aliyun.com]
 * @version 1.0
 * @since 2020/6/8 17:19:50
 */
public class AccessLogImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        // 需要处理的注解名称
        Class<?> annotationType = EnableAccessLog.class;
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(
                annotationMetadata.getAnnotationAttributes(annotationType.getName(), false)
        );
        // 常规场景下,该参数永远不会为null.
        assert annotationAttributes != null;
        boolean global = annotationAttributes.getBoolean("enableGlobal");
        return new String[]{
                (global ? MappingAnnotationAspectPointCutAdvisor.class : AccessLogAnnotationAspectPointCutAdvisor.class).getCanonicalName()
        };
    }
}

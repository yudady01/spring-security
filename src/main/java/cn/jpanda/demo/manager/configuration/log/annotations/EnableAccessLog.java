package cn.jpanda.demo.manager.configuration.log.annotations;

import cn.jpanda.demo.manager.configuration.log.AccessLogAutoConfiguration;
import cn.jpanda.demo.manager.configuration.log.AccessLogImportSelector;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 是否开启日志
 *
 * @author HanQi [Jpanda@aliyun.com]
 * @version 1.0
 * @since 2020/6/8 10:59:01
 */
@Component
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({AccessLogImportSelector.class, AccessLogAutoConfiguration.class})
public @interface EnableAccessLog {
    /**
     * 是否拦截所有的请求
     */
    boolean enableGlobal() default false;
}

package cn.jpanda.demo.manager.configuration.log.annotations;

import java.lang.annotation.*;

/**
 * 日志注解
 *
 * @author HanQi [Jpanda@aliyun.com]
 * @version 1.0
 * @since 2020/6/6 15:39:59
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLog {
    /**
     * 日志名称,可选
     */
    String value() default "";
}

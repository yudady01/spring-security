package cn.jpanda.demo.manager.configuration.security.annotations;

import java.lang.annotation.*;

/**
 * 标注在方法参数上，用于动态注入当前登录用户对象
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface User {
}

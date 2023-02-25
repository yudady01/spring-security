package cn.jpanda.demo.manager.configuration.mvc.jackson.session;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.*;

/**
 * 系统针对标注了该注解的属性，尝试从{@link javax.servlet.http.HttpSession}中获取{@link #key()}值对应的属性，并转换为对应的对象。
 */
@Target({ElementType.PARAMETER,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@JacksonAnnotationsInside
@JsonSerialize(using = SessionValueJsonSerializer.class)
@JsonDeserialize(using = SessionValueJsonDeserializer.class)
public @interface SessionValue {
    /**
     * 存放在session中的key值
     * @return  key值
     */
    String key()default "";

    /**
     * 该属性是否未必须存在的
     * @return 属性是否未必须存在的
     */
    boolean required()default true;

    /**
     * 默认的序列化异常处理器
     * @return 异常处理器类型
     */
    Class<? extends SessionValueSerializerExceptionHandler> exceptionHandler()default NothingSessionValueSerializerExceptionHandler.class;
}

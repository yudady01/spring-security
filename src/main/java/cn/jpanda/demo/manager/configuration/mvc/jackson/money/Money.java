package cn.jpanda.demo.manager.configuration.mvc.jackson.money;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.*;

/**
 * 金额注解
 * @author HanQi(Jpanda@aliyun.com)
 * @since  2019年12月5日11:25:55
 */
@Target({ElementType.PARAMETER,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@JacksonAnnotationsInside
@JsonSerialize(using = MoneyJsonSerializer.class)
@JsonDeserialize(using = MoneyJsonDeserializer.class)
public @interface Money {
    /**
     * 价格小数点后保留位数
     */
    int precision() default 2;

    /**
     * 数字格式，当有值时，优先级大于{@link #precision()}
     */
    String decimalFormat() default "";

    /**
     * 价格转换策略
     */
    MoneyConvertStrategyEnum strategy()default MoneyConvertStrategyEnum.PENNY_2_YUAN;
}

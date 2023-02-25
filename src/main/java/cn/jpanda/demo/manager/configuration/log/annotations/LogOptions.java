package cn.jpanda.demo.manager.configuration.log.annotations;


import cn.jpanda.demo.manager.configuration.log.handler.filters.Filter;
import cn.jpanda.demo.manager.configuration.log.handler.filters.NoneFilter;

/**
 * 日志选项,该选项可以覆盖,日志的默认行为
 *
 * @author HanQi [Jpanda@aliyun.com]
 * @version 1.0
 * @since 2020/6/8 9:57:13
 */
public @interface LogOptions {

    /**
     * 是否记录请求地址,核心数据,建议开启.
     */
    boolean uri() default true;

    /**
     * 是否记录请求头,用于特殊场景,默认关闭
     */
    boolean headers() default false;

    /**
     * 请求头过滤器
     */
    Class<? extends Filter> headersFilter() default NoneFilter.class;

    /**
     * 是否记录请求方法
     */
    boolean httpMethod() default true;

    /**
     * 是否记录命中方法
     */
    boolean signature() default true;

    /**
     * 是否记录请求参数
     */
    boolean params() default true;

    /**
     * 是否记录请求来源IP
     */
    boolean ip() default true;

    /**
     * 是否记录请求耗时
     */
    boolean duration() default true;

    /**
     * 是否记录响应状态
     */
    boolean status() default true;

    /**
     * 是否记录响应体
     */
    boolean result() default false;

    /**
     * 日志关键信息
     */
    boolean errMessage() default true;

    /**
     * 日志堆栈信息
     */
    boolean errTrace() default false;

    /**
     * 调用时间
     */
    boolean requestTime() default true;
}

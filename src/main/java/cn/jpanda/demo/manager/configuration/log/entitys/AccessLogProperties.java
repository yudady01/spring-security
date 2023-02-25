package cn.jpanda.demo.manager.configuration.log.entitys;

import cn.jpanda.demo.manager.configuration.log.handler.filters.Filter;
import cn.jpanda.demo.manager.configuration.log.handler.filters.NoneFilter;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @author HanQi [Jpanda@aliyun.com]
 * @version 1.0
 * @since 2020/6/6 15:34:35
 */
@Data
@ConfigurationProperties("access.log")
public class AccessLogProperties implements Serializable, Cloneable {

    /**
     * 是否记录请求地址,核心数据,建议开启.
     */
    private boolean uri = true;
    /**
     * 是否记录请求头,用于特殊场景,默认关闭
     */
    private boolean headers = false;
    /**
     * 请求头过滤器
     */
    private Class<? extends Filter> headersFilter = NoneFilter.class;
    /**
     * 是否记录请求方法
     */
    private boolean httpMethod = true;
    /**
     * 是否记录命中方法
     */
    private boolean signature = true;
    /**
     * 是否记录请求参数
     */
    private boolean params = true;

    /**
     * 是否记录请求来源IP
     */
    private boolean ip = true;
    /**
     * 是否记录请求耗时
     */
    private boolean duration = true;
    /**
     * 是否记录响应状态
     */
    private boolean status = true;
    /**
     * 是否记录响应体
     */
    private boolean result = true;

    /**
     * 关键异常日志
     */
    private boolean errMessage = true;
    /**
     * 因此堆栈信息
     */
    private boolean errTrace = false;


    /**
     * 调用时间
     */
    private boolean requestTime;

    @Override
    public AccessLogProperties clone() {

        AccessLogProperties clone = new AccessLogProperties();
        clone.setUri(this.uri);
        clone.setHeaders(this.headers);
        clone.setHeadersFilter(this.headersFilter);
        clone.setHttpMethod(this.httpMethod);
        clone.setSignature(this.signature);
        clone.setParams(this.params);
        clone.setIp(this.ip);
        clone.setDuration(this.duration);
        clone.setStatus(this.status);
        clone.setResult(this.result);
        clone.setErrMessage(this.errMessage);
        clone.setErrTrace(this.errTrace);
        clone.setRequestTime(this.requestTime);
        return clone;
    }
}

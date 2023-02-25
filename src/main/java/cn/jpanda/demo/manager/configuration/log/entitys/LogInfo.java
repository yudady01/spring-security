package cn.jpanda.demo.manager.configuration.log.entitys;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LogInfo {
    /**
     * 请求路径
     */
    private String uri;

    /**
     * 请求方法
     */
    private HttpMethod httpMethod;

    /**
     * 调用时间戳
     */
    private Date requestTime;

    /**
     * 请求头
     */
    private Map<String, String> headers;

    /**
     * 命中Controller
     */
    private Signature signature;

    /**
     * 请求参数
     */
    private Param[] args;
    /**
     * 方法描述
     */
    private String description;
    /**
     * 请求来源IP
     */
    private String requestIp;

    /**
     * 请求耗时
     */
    private Long duration;

    /**
     * 响应状态
     */
    private HttpStatus responseStatus;

    /**
     * 响应数据
     */
    private Object result;

    /**
     * 错误日志
     */
    private Object errMessage;

    /**
     * 异常堆栈信息
     */
    private Object errTrace;


}

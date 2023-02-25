package cn.jpanda.demo.manager.configuration.log.entitys;


import lombok.Builder;
import lombok.Data;

/**
 * 请求参数对象
 */
@Data
@Builder
public class Param {
    /**
     * 参数名称[形]
     */
    private String argName;

    /**
     * 参数简单名称
     */
    private String name;
    /**
     * 参数类型[形]
     */
    private Class<?> type;
    /**
     * 参数值[实]
     */
    private Object value;

}

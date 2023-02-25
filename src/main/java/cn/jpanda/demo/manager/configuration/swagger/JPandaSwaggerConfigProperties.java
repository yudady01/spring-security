package cn.jpanda.demo.manager.configuration.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Swagger2配置类
 */
@Data
@Component
@ConfigurationProperties(prefix = "jpanda.swagger.config")
public class JPandaSwaggerConfigProperties {
    /**
     * 是否启用Swagger
     */
    private boolean enable = false;

    /**
     * 是否限制访问权限
     */
    private boolean auth = true;

    /**
     * 基础包名
     */
    private String basePackage;

    /**
     * 标题
     */
    private String title;
    /**
     * 描述
     */
    private String description;
    /**
     * 版本
     */
    private String version;

}

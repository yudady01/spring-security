package cn.jpanda.demo.manager.configuration.exception;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "jpanda.exception.handler")
public class JPandaExceptionConfigProperties {
    private boolean returnErrorInfo;
}

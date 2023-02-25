package cn.jpanda.demo.manager.component.upload;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "jpanda.file.upload")
public class FileUploadConfig {

    /**
     * 数据存放目录
     */
    private String directory;

}

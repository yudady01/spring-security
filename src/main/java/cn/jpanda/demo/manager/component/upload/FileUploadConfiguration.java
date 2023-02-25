package cn.jpanda.demo.manager.component.upload;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(FileUploadConfig.class)
public class FileUploadConfiguration {

    @Bean
    public FileUpload fileUpload() {
        return new LocalFileUpload();
    }
}

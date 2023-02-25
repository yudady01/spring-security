package cn.jpanda.demo.manager;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Slf4j
@EnableSwagger2
@EnableWebSecurity
@SpringBootApplication
@MapperScan("cn.jpanda.demo.manager.dao")
@EnableConfigurationProperties
@EnableGlobalMethodSecurity(securedEnabled = true,jsr250Enabled = true,prePostEnabled = true)
public class JPandaManagerApplication {

    private static final String APPLICATION_NAME = "JPanda Spring Boot Security 快速开发";

    public static void main(String[] args) {
        log.debug("Prepare to boot the program named: {}", APPLICATION_NAME);
        SpringApplication.run(JPandaManagerApplication.class, args);
        log.debug("{} Program is Started.", APPLICATION_NAME);
    }

}

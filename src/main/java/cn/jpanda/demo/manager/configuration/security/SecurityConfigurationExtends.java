package cn.jpanda.demo.manager.configuration.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public interface SecurityConfigurationExtends {
    void configure(HttpSecurity http) throws Exception;
}

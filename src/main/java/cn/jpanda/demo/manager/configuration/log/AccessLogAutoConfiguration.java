package cn.jpanda.demo.manager.configuration.log;

import cn.jpanda.demo.manager.configuration.log.entitys.AccessLogProperties;
import cn.jpanda.demo.manager.configuration.log.persistence.DefaultLogPersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author HanQi [Jpanda@aliyun.com]
 * @version 1.0
 * @since 2020/6/8 14:50:15
 */
@Import({AccessLogProperties.class})
public class AccessLogAutoConfiguration {
    @Bean
    public OncePerRequestFilter oncePerRequestFilter() {
        return new AccessLogOncePerRequestFilter(new DefaultLogPersistence());
    }
}

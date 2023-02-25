package cn.jpanda.demo.manager.configuration.mvc;

import cn.jpanda.demo.manager.configuration.mvc.jackson.money.MoneyMethodArgumentResolver;
import cn.jpanda.demo.manager.configuration.mvc.jackson.session.SessionValueMethodArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new SessionValueMethodArgumentResolver());
        resolvers.add(new MoneyMethodArgumentResolver());
    }
}

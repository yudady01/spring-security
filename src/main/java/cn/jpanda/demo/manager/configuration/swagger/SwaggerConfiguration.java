package cn.jpanda.demo.manager.configuration.swagger;

import cn.jpanda.demo.manager.configuration.security.SecurityConfigurationExtends;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * Swagger配置Bean
 */
@Configuration
@ConditionalOnProperty(prefix = "jpanda.swagger.config", name = "enable", havingValue = "true")
@Import(JPandaSwaggerConfigProperties.class)
public class SwaggerConfiguration {

    @Resource
    private JPandaSwaggerConfigProperties config;

    @Bean
    public Docket api() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(config.getBasePackage()))
                .paths(PathSelectors.any())
                .build();

        if (config.isAuth()) {
            docket
                    .securitySchemes(securitySchemes())
                    .securityContexts(securityContexts());
        }
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(config.getTitle())
                .description(config.getDescription())
                .version(config.getVersion())
                .build();
    }

    private List<ApiKey> securitySchemes() {
        return Collections.singletonList(
                new ApiKey("Authorization", "Authorization", "header"));
    }

    private List<SecurityContext> securityContexts() {
        return Collections.singletonList(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build()
        );
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference("Authorization", authorizationScopes));
    }

    @Bean
    @ConditionalOnProperty(prefix = "jpanda.swagger.config", name = "auth", havingValue = "false")
    public SecurityConfigurationExtends swaggerSecurityConfiguration() {
        return http -> http.authorizeRequests().antMatchers("/swagger-ui.html").permitAll()

                .antMatchers("/swagger-resources/**").permitAll()

                .antMatchers("/images/**").permitAll()

                .antMatchers("/webjars/**").permitAll()

                .antMatchers("/v2/api-docs").permitAll()

                .antMatchers("/configuration/ui").permitAll()

                .antMatchers("/configuration/security").permitAll();
    }
}

package fun.baozi.boot.core.config;

import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger 配置
 * @author baozi
 * @date 2019-09-22
 */
@Configuration
@EnableSwagger2
@ComponentScan("fun.baozi.boot")
public class SwaggerConfig {

    private static final String PROD_ENV = "prod";

    @Value("${spring.profiles.active}")
    private String environment;

    @Bean
    public Docket createRestApi() {
        if (Strings.isBlank(environment) || PROD_ENV.equals(environment)) {
            return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(onlineApiInfo())
                    .select()
                    .paths(PathSelectors.none())
                    .build();
        }
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))                         //这里采用包含注解的方式来确定要显示的接口
//                .apis(RequestHandlerSelectors.any())
//                .apis(RequestHandlerSelectors.basePackage("fun.baozi.boot"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("博客接口")
                .description("博客")
                .contact(
                        new Contact("baozi", "http://baozi.pro", "ms915818993@163.com")
                )
                .version("1.0.0")
                .build();
    }

    private ApiInfo onlineApiInfo() {
        return new ApiInfoBuilder()
                .title(Strings.EMPTY)
                .description(Strings.EMPTY)
                .version(Strings.EMPTY)
                .contact(new Contact(Strings.EMPTY, Strings.EMPTY, Strings.EMPTY))
                .build();
    }

}

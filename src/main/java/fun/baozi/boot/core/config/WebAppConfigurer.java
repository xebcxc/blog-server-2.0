package fun.baozi.boot.core.config;

import fun.baozi.boot.core.intercepter.RequestInterceptor;
import fun.baozi.boot.core.intercepter.StatisticsInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * Web App应用配置
 * @author baozi
 * 2019-05-23
 */
@Configuration
public class WebAppConfigurer extends WebMvcConfigurationSupport {

    @Value("${spring.resources.static-locations}")
    private String resourceStaticLocations;

    /**
     * 解决在StatisticsInterceptor 拦截时Autowired失败
     * @return StatisticsInterceptor
     */
    @Bean
    public StatisticsInterceptor statisticsInterceptor() {
        return new StatisticsInterceptor();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .exposedHeaders(HttpHeaders.SET_COOKIE).maxAge(3600L);
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/api/static/image/**")
                .addResourceLocations(resourceStaticLocations);
//        registry.addResourceHandler("/api/image/**").addResourceLocations("file:/opt/app/static/");
        super.addResourceHandlers(registry);
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则, 这里拦截 /api 后面的全部链接
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new RequestInterceptor())
                .addPathPatterns("/api/**");
        registry.addInterceptor(statisticsInterceptor())
                .addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}

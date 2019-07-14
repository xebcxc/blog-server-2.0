package pro.meisen.boot.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import pro.meisen.boot.core.intercepter.RequestInterceptor;

/**
 * Web App应用配置
 * @author meisen
 * 2019-05-23
 */
@Configuration
public class WebAppConfigurer extends WebMvcConfigurationSupport {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedMethods("*").allowedHeaders("*")
                .allowCredentials(true)
                .exposedHeaders(HttpHeaders.SET_COOKIE).maxAge(3600L);
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则, 这里拦截 /api 后面的全部链接
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new RequestInterceptor()).addPathPatterns("/api/**");
        super.addInterceptors(registry);
    }
}

package pro.meisen.boot.core.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pro.meisen.boot.ext.shiro.filter.ShiroRequestFilter;
import pro.meisen.boot.ext.shiro.realm.AuthRealm;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author meisen
 * 2019-07-22
 */
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(securityManager);
        Map<String, Filter> filterMap = filterFactoryBean.getFilters();
        filterMap.put("shiroRequest", shiroRequestFilter());
        //  这一步我觉得可以不用要
        filterFactoryBean.setFilters(filterMap);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //注意过滤器配置顺序 不能颠倒
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了，登出后跳转配置的loginUrl
        filterChainDefinitionMap.put("/api/user/logout", "logout");
        filterChainDefinitionMap.put("/api/static/image/**", "anon");
        filterChainDefinitionMap.put("/api/static/file/upload", "anon");
        filterChainDefinitionMap.put("/api/blog/**", "anon");
        filterChainDefinitionMap.put("/api/user/login", "anon");
        filterChainDefinitionMap.put("/api/user/register", "anon");
        filterChainDefinitionMap.put("/api/**", "authc");
//        filterChainDefinitionMap.put("/api/**", "anon");
        //配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
        filterFactoryBean.setLoginUrl("/api/user/unauth");
        filterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return filterFactoryBean;
    }

    /**
     * 凭证匹配器
     * @return HashedCredentialsMatcher
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 设置散列算法为md5
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        // 散列次数
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }

    @Bean
    public AuthorizingRealm authorizingRealm() {
        AuthRealm authRealm = new AuthRealm();
        authRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return authRealm;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
        defaultSecurityManager.setRealm(authorizingRealm());
        return defaultSecurityManager;
    }

    @Bean
    public ShiroRequestFilter shiroRequestFilter() {
        return new ShiroRequestFilter();
    }

}

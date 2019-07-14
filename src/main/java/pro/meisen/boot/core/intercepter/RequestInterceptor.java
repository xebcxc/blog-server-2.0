package pro.meisen.boot.core.intercepter;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求拦截器
 * 拦截器需要注册
 * @author meisen
 * 2018-08-14
 */
public class RequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String origin = request.getHeader("origin");
        // 添加跨域header
        response.setHeader("Access-Control-Allow-Origin", origin);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,Access-Token,X-Requested-With,Authorization");
        response.setHeader("Access-Control-Expose-Headers", "*");
        if (RequestMethod.OPTIONS.name().equals(request.getMethod())) {
            response.setStatus(200);
            return true;
        }
        return true;
    }
}

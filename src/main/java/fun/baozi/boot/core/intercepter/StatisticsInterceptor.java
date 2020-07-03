package fun.baozi.boot.core.intercepter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import fun.baozi.boot.ext.redis.RedisKey;
import fun.baozi.boot.ext.redis.RedisOperation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author baozi
 * 2019-07-26
 */
public class StatisticsInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisOperation<String> redisOperation;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String sessionId = session.getId();
        // 使用redis 的hyperLog统计用户访问量
        redisOperation.pfAdd(RedisKey.UV.getKey(), sessionId);
        return true;
    }
}

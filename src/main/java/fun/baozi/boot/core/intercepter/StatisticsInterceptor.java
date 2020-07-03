package fun.baozi.boot.core.intercepter;

import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author baozi
 * 2019-07-26
 */
public class StatisticsInterceptor implements HandlerInterceptor {
//    @Autowired
//    private RedisOperation<String> redisOperation;
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        HttpSession session = request.getSession();
//        String sessionId = session.getId();
//        // 使用redis 的hyperLog统计用户访问量
//        redisOperation.pfAdd(RedisKey.UV.getKey(), sessionId);
//        return true;
//    }
}

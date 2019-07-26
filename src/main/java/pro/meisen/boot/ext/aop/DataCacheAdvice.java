package pro.meisen.boot.ext.aop;

import com.alibaba.fastjson.JSON;
import com.sun.org.apache.regexp.internal.RE;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pro.meisen.boot.ext.annotation.DataCache;
import pro.meisen.boot.ext.redis.RedisKey;
import pro.meisen.boot.ext.redis.RedisOperation;
import pro.meisen.boot.web.req.PageModel;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author meisen
 * 2019-07-25
 */
@Component
public class DataCacheAdvice {
    @Autowired
    private RedisOperation<String> redisOperation;


    protected Object beforeProcess(ProceedingJoinPoint point) {
        Method method = getMethod(point);
        if (method == null) {
            return null;
        }
        DataCache dataCache = method.getAnnotation(DataCache.class);
        if (null == dataCache) {
            return null;
        }
        String redisKey = dataCache.key();
        String value = redisOperation.get(redisKey);
        return JSON.toJSON(value);
    }

    public Object afterProcess(ProceedingJoinPoint point, Object result) throws IllegalAccessException, InstantiationException {
        Method method = getMethod(point);
        if (method == null) {
            return result;
        }
        DataCache dataCache = method.getAnnotation(DataCache.class);
        if (null == dataCache) {
            return result;
        }
        // 获取redisKey
        String redisKey = dataCache.key();
        // 判断是否需要加参数
        redisKey = getRedisKey(point, redisKey);
        if (result instanceof List) {
            redisOperation.set(redisKey, JSON.toJSONString(result));
        }
        return result;
    }

    /**
     * 获取缓存
     * @param point 横切点
     * @param redisKey 缓存key
     * @return
     */
    private String getRedisKey(ProceedingJoinPoint point, String redisKey) {
        int pageNum = 0;
        int pageSize = 0;
        String afterKey = null;
        Object[] args = point.getArgs();
        for (Object obj : args) {
            if (obj instanceof PageModel) {
                PageModel pageModel = (PageModel) obj;
                if (pageModel.getPageNum() != null && pageModel.getPageSize() != null) {
                    pageNum = pageModel.getPageNum();
                    pageSize = pageModel.getPageSize();
                    break;
                }
            } else if (obj instanceof String ) {
                afterKey = (String) obj;
            } else if (obj instanceof Long) {
                afterKey = ((Long) obj).toString();
            }
        }
        if (0 != pageSize) {
            afterKey = pageNum + "_" + pageSize;
        }
        redisKey += afterKey;
        return redisKey;
    }


    protected Method getMethod(final ProceedingJoinPoint jp) {
        Object[] args = jp.getArgs();
        Class[] argTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            argTypes[i] = args[i].getClass();
        }
        try {
            return jp.getTarget().getClass().getMethod(jp.getSignature().getName(), argTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}

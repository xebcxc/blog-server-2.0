package pro.meisen.boot.ext.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


/**
 * @author meisen
 * 2019-07-25
 */
@Aspect
@Component
public class CacheAop extends DataCacheAdvice {

    @Pointcut("@annotation(pro.meisen.boot.ext.annotation.DataCache)")
    public void cacheDataPointCut(){}


    @Around("cacheDataPointCut()")
    public Object cacheData(final ProceedingJoinPoint jp) throws Throwable {
        // 处理之前, 检查缓存
        Object result = beforeProcess(jp);
        if (result == null ) {
            result = jp.proceed();
        }
        afterProcess(jp, result);
        return result;
    }
}

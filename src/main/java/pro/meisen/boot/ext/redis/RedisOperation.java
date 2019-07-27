package pro.meisen.boot.ext.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis操作
 * @author meisen
 * 2019-05-23
 */
@Component
public class RedisOperation<T> {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private HashOperations<String, String, T> hashOperations;
    @Autowired
    private ListOperations<String, T> listOperations;
    @Autowired
    private ZSetOperations<String, T> zSetOperations;
    @Autowired
    private ValueOperations<String, T> valueOperations;
    @Autowired
    private SetOperations<String, T> setOperations;
    @Autowired
    private HyperLogLogOperations<String, T> hyperLogLogOperations;

    public void hSet(String key, String hashKey, T t) {
        hashOperations.put(key, hashKey, t);
    }

    public void hSet(String key, String hashKey, T t, long expire) {
        hashOperations.put(key, hashKey, t);
        if (expire != -1) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    public Long hDel(String key, String hashKeys) {
        return hashOperations.delete(key, hashKeys);
    }

    public T hGet(String key, String hashKey) {
        return hashOperations.get(key, hashKey);
    }

    public Set<String> hGet(String key) {
        return hashOperations.keys(key);
    }

    public List<T> hGetValue(String key) {
        return hashOperations.values(key);
    }

    public boolean hExits(String key, String hashKey) {
        return hashOperations.hasKey(key, hashKey);
    }

    public Long hSize(String key) {
        return hashOperations.size(key);
    }

    public Set<String> hKeys(String key) {
        return hashOperations.keys(key);
    }

    public Long lPush(String key, T value) {
        return listOperations.leftPush(key, value);
    }

    public Long rPush(String key, T value) {
        return listOperations.rightPush(key, value);
    }

    public T lPop(String key) {
        return listOperations.leftPop(key);
    }

    public T rPop(String key) {
        return listOperations.rightPop(key);
    }

    public boolean zSet(String key, T value, double source) {
        return zSetOperations.add(key, value, source);
    }

    public Double zSize(String key, T t) {
        return zSetOperations.score(key, t);
    }

    public double zScore(String key, T value) {
        return zSetOperations.score(key, value);
    }

    public Set<T> zRange(String key, long start, long end) {
        return zSetOperations.range(key, start, end);
    }

    public Set<T> reverseRange(String key, long start, long end) {
        return zSetOperations.reverseRange(key, start, end);
    }

    public Long zDel(String key, T value) {
        return zSetOperations.remove(key, value);
    }

    public Double zIncr(String key, T t, double delta) {
        return zSetOperations.incrementScore(key, t, delta);
    }

    public void vSet(String key, T t) {
        valueOperations.set(key, t);
    }

    public void vSet(String key, T t, long expire) {
        valueOperations.set(key, t, expire);
    }

    public T vGet(String key) {
        return valueOperations.get(key);
    }

    public Long vSize(String key) {
        return valueOperations.size(key);
    }


    public Long set(String key, T t) {
        return setOperations.add(key, t);
    }

    public Long size(String key) {
        return setOperations.size(key);
    }

    public T get(String key) {
        return setOperations.pop(key);
    }

    public Long pfAdd(String key, T t) {
       return hyperLogLogOperations.add(key, t);
    }

    public Long pdCount(String key) {
        return hyperLogLogOperations.size(key);
    }

}

package pro.meisen.boot.helper;

import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pro.meisen.boot.core.redis.RedisKeyEnum;
import pro.meisen.boot.core.redis.RedisOperation;
import pro.meisen.boot.domain.Article;
import pro.meisen.boot.dao.service.ArticleService;

/**
 * 缓存类
 * @author meisen
 * 2019-05-23
 */
@Component
public class CacheHelper {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private RedisOperation<String> redisOperation;

    @Autowired
    private RedisOperation<String> stringOperation;

    /**
     * 缓存文章
     * @param article 文章
     */
    public void cacheArticle(Article article) {
        if (null != article && Strings.isEmpty(article.getArticleId())) {
            String id = article.getArticleId();
            redisOperation.hSet(RedisKeyEnum.BLOG.name(), id, JSON.toJSONString(article));
        }
    }
}

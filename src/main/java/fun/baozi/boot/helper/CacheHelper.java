package fun.baozi.boot.helper;

import com.alibaba.fastjson.JSON;
import fun.baozi.boot.domain.Article;
import fun.baozi.boot.ext.redis.RedisKey;
import fun.baozi.boot.ext.redis.RedisOperation;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import fun.baozi.boot.dao.service.ArticleService;

/**
 * 缓存类
 * @author baozi
 * 2019-05-23
 */
@Component
public class CacheHelper {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private RedisOperation<String> redisOperation;

    /**
     * 缓存文章
     * @param article 文章
     */
    public void cacheArticle(Article article) {
        if (null != article && Strings.isEmpty(article.getArticleId())) {
            String id = article.getArticleId();
            redisOperation.hSet(RedisKey.BLOG.getKey(), id, JSON.toJSONString(article));
        }
    }
}

package fun.baozi.boot.ext.redis;

import fun.baozi.boot.domain.Article;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import fun.baozi.boot.dao.service.ArticleService;
import fun.baozi.boot.helper.CacheHelper;

import java.util.List;

@Component
public class InitializingCache implements InitializingBean {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CacheHelper cacheHelper;

    private void initArticleCache() {
        List<Article> allArticles = articleService.listPublish();
        allArticles.forEach(cacheHelper::cacheArticle);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initArticleCache();
    }
}

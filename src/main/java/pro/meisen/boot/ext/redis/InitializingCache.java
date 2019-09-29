package pro.meisen.boot.ext.redis;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pro.meisen.boot.domain.Article;
import pro.meisen.boot.dao.service.ArticleService;
import pro.meisen.boot.helper.CacheHelper;

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

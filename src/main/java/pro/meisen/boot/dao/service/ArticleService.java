package pro.meisen.boot.dao.service;

import com.github.pagehelper.Page;
import pro.meisen.boot.dao.TagSearchParam;
import pro.meisen.boot.dao.service.basic.BasicService;
import pro.meisen.boot.domain.Article;
import pro.meisen.boot.web.req.BlogSearchModel;
import pro.meisen.boot.web.req.TagSearchModel;

import java.util.List;

public interface ArticleService extends BasicService<Article> {

    Article findByArticleId(String articleId);


    List<Article> listAllArticle() ;

    List<Article> listByCondition(Article article);

    Page<Article> listArticleWithPage(BlogSearchModel searchRequest);

    /**
     * 通过id查询所有的文章, 这里就不分页了
     * @param idList id集合
     * @return Article集合
     */
    List<Article> listByIds(List<Long> idList);

    Page<Article> listByIdListWithPage(TagSearchParam param);

    List<Article> listByArticleIdList(List<String> idList);

    int batchUpdate(List<Article> articleList);

}

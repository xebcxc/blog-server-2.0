package pro.meisen.boot.dao.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageRowBounds;
import org.apache.ibatis.session.RowBounds;
import pro.meisen.boot.domain.Article;
import pro.meisen.boot.web.req.BlogSearchRequest;
import pro.meisen.boot.web.req.PageRequest;

import java.util.List;

public interface ArticleService extends BasicService<Article> {

    Article findByArticleId(String articleId);


    List<Article> listAllArticle() ;

    List<Article> listByCondition(Article article);

    Page<Article> listArticleWithPage(BlogSearchRequest searchRequest);

    /**
     * 通过id查询所有的文章, 这里就不分页了
     * @param idList id集合
     * @return Article集合
     */
    List<Article> listByIds(List<Long> idList);

    List<Article> listByArticleIdList(List<String> idList);

    int batchUpdate(List<Article> articleList);

}

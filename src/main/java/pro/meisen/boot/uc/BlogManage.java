package pro.meisen.boot.uc;

import com.github.pagehelper.Page;
import pro.meisen.boot.domain.Article;
import pro.meisen.boot.web.req.BlogSearchModel;
import pro.meisen.boot.web.req.TagSearchModel;
import pro.meisen.boot.web.res.ResultPageData;

import java.util.List;
import java.util.Map;

/**
 * @author meisen
 * 2019-05-23
 */
public interface BlogManage {

    ResultPageData<Article> listArticleWithPage(BlogSearchModel request);

    Article getDetailByArticleId(String articleId);
    Article getDetailByArticleIdWithCache(String articleId);

    ResultPageData<Article> listByTagName(TagSearchModel searchModel);

    /**
     * 添加文章
     * @param article 新文章
     */
    void addArticle(Article article);

    /**
     * 删除文章
     * @param id 文章id
     */
    void deleteArticleById(Long id);

    void deleteArticleByArticleId(String articleId);

    Map<String, List<Article>> achieveBlog(List<Article> articleList);


    Article updateArticle(Article article);

}

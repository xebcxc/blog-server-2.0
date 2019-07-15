package pro.meisen.boot.uc;

import com.github.pagehelper.Page;
import pro.meisen.boot.domain.Article;
import pro.meisen.boot.web.req.BlogSearchRequest;

import java.util.List;

/**
 * @author meisen
 * 2019-05-23
 */
public interface BlogManage {

    Page<Article> listArticleWithPage(BlogSearchRequest request);

    Article getDetailByArticleId(String articleId);

    List<Article> listByTagName(String tagName);

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

}

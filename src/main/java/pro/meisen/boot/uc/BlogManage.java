package pro.meisen.boot.uc;

import pro.meisen.boot.domain.Article;
import pro.meisen.boot.web.req.BlogSearchForm;
import pro.meisen.boot.web.req.TagSearchForm;
import pro.meisen.boot.web.res.PageData;

import java.util.List;
import java.util.Map;

/**
 * @author meisen
 * 2019-05-23
 */
public interface BlogManage {

    PageData<Article> listArticleWithPage(BlogSearchForm request);

    /**
     * 查询所有文章
     * @param text 文本
     * @param pageNum 页数
     * @return 符合条件的页数
     */
    PageData<Article> searchEverything(String text, int pageNum, int pageSize);

    Article getDetailByArticleId(String articleId);


    Article getDetailByArticleIdWithIncrVisit(String articleId);

    PageData<Article> listByTagName(TagSearchForm searchModel);

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

    /**
     * 文章归档
     * key: 年份
     * value: 当前类下面的文章
     * @param articleList 原始文章
     * @return 归档后的文章
     */
    Map<String, List<Article>> achieveBlog(List<Article> articleList);

    /**
     * 更显稳重
     * @param article
     * @return
     */
    Article updateArticle(Article article);

}

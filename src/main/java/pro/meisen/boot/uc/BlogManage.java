package pro.meisen.boot.uc;

import pro.meisen.boot.domain.Article;

/**
 * @author meisen
 * 2019-05-23
 */
public interface BlogManage {
    /**
     * 添加文章
     * @param article 新文章
     */
    void addArticle(Article article);

    /**
     * 删除文章
     * @param article 旧文章
     */
    void deleteArticle(Article article);

}

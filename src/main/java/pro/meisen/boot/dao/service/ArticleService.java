package pro.meisen.boot.dao.service;

import pro.meisen.boot.dao.TagSearchParam;
import pro.meisen.boot.dao.service.basic.BasicService;
import pro.meisen.boot.domain.Article;
import pro.meisen.boot.web.req.BlogSearchForm;
import pro.meisen.boot.web.res.PageData;

import java.util.List;

public interface ArticleService extends BasicService<Article> {

    Article findByArticleId(String articleId);

    List<Article> listPublish() ;

    List<Article> listByCondition(Article article);

    PageData<Article> listArticleWithPage(BlogSearchForm searchRequest);

    /**
     * 通过文字查询所有内容
     * @param text 文字
     * @return 内容
     */
    PageData<Article> searchEverything(String text, int pageNum, int pageSize);

    /**
     * 通过id查询所有的文章, 这里就不分页了
     * @param idList id集合
     * @return Article集合
     */
    List<Article> listByIds(List<Long> idList);

    PageData<Article> listByIdListWithPage(TagSearchParam param);

    List<Article> listByArticleIdList(List<String> idList);

    int batchUpdate(List<Article> articleList);

    int logicDeleteById(Long id);

    /**
     * 置顶文章
     * @param articleId 文章id
     */
    void sortArticle(String articleId, Byte sort);

    void increaseVisit(Long articleId);

}

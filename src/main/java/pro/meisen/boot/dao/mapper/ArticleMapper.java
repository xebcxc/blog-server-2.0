package pro.meisen.boot.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pro.meisen.boot.dao.TagSearchParam;
import pro.meisen.boot.domain.Article;
import pro.meisen.boot.web.req.BlogSearchModel;

import java.util.List;

@Repository
public interface ArticleMapper extends BasicMapper<Long, Article> {

    Article findByArticleId(@Param("articleId") String articleId);
    // 获取所有
    List<Article> listAllArticles();

    // 根据条件查询
    List<Article> listByCondition(@Param("condition") Article article);

    List<Article> listByPage(@Param("condition") BlogSearchModel searchModel);

    List<Article> listByIds(@Param("idList")List<Long> idList);

    List<Article> listByIdListWithPage(TagSearchParam param);

    List<Article> listByArticleIdList(@Param("articleIdList")List<String> articleIdList);

    int batchUpdate(@Param("articleList") List<Article> articleList);

    int batchInsert(@Param("articleList") List<Article> articleList);
}
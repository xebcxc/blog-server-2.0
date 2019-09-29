package pro.meisen.boot.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pro.meisen.boot.dao.TagSearchParam;
import pro.meisen.boot.domain.Article;
import pro.meisen.boot.web.req.BlogSearchForm;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ArticleMapper extends Mapper<Article> {

    List<Article> selectByCondition(BlogSearchForm searchModel);

    Long countArticles(BlogSearchForm searchModel);

    List<Article> listByIds(@Param("idList")List<Long> idList);

    List<Article> listByIdListWithPage(TagSearchParam param);

    Long countByIdList(TagSearchParam param);

    List<Article> listByArticleIdList(@Param("articleIdList")List<String> articleIdList);

    int batchUpdate(@Param("articleList") List<Article> articleList);

    int batchInsert(@Param("articleList") List<Article> articleList);
}
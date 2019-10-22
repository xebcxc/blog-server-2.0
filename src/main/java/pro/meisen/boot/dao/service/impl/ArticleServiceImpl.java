package pro.meisen.boot.dao.service.impl;

import com.github.pagehelper.PageHelper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import pro.meisen.boot.dao.TagSearchParam;
import pro.meisen.boot.dao.mapper.ArticleMapper;
import pro.meisen.boot.dao.service.ArticleService;
import pro.meisen.boot.dao.service.basic.BasicServiceImpl;
import pro.meisen.boot.domain.Article;
import pro.meisen.boot.domain.enums.EArticlePublishStatusEnum;
import pro.meisen.boot.domain.enums.EArticleSortStatusEnum;
import pro.meisen.boot.web.req.BlogSearchForm;
import pro.meisen.boot.web.res.PageData;
import tk.mybatis.mapper.common.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ArticleServiceImpl extends BasicServiceImpl<Article> implements ArticleService {

    @Autowired
    private ArticleMapper mapper;

    @Override
    public Mapper<Article> getMapper() {
        return mapper;
    }

    @Override
    public Article findByArticleId(String articleId) {
        if (Strings.isEmpty(articleId)) {
            return null;
        }
        Article article = new Article();
        article.setArticleId(articleId);
        article.setDelete(false);
        return mapper.selectOne(article);
    }

    @Override
    public List<Article> listPublish() {
        Article record = new Article();
        record.setPublish(EArticlePublishStatusEnum.PUBLISH.isCode());
        record.setDelete(false);
        return notEmptyList(mapper.select(record));
    }

    @Override
    public List<Article> listByCondition(Article article) {
        return notEmptyList(mapper.select(article));
    }

    @Override
    public PageData<Article> listArticleWithPage(BlogSearchForm request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize())
                .count(true);
        List<Article> articleList = mapper.selectByCondition(request);
        PageHelper.clearPage();
        Long count = mapper.countArticles(request);
        PageData<Article> pageData = new PageData<>();
        pageData.setData(notEmptyList(articleList));
        pageData.setCount(Objects.isNull(count) ? 0L : count);
        return pageData;
    }

    @Override
    public List<Article> listByIds(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return new ArrayList<>();
        }
        return notEmptyList(mapper.listByIds(idList));
    }

    @Override
    public PageData<Article> listByIdListWithPage(TagSearchParam param) {
        PageData<Article> pageData = new PageData<>();
        if (Objects.isNull(param) || CollectionUtils.isEmpty(param.getIdList())) {
            pageData.setData(new ArrayList<>());
            pageData.setCount(0L);
            return pageData;
        }
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        List<Article> articleList = mapper.listByIdListWithPage(param);
        PageHelper.clearPage();
        Long count = mapper.countByIdList(param);
        pageData.setData(articleList);
        pageData.setCount(Objects.isNull(count) ? 0L : count);
        return pageData;
    }

    @Override
    public List<Article> listByArticleIdList(List<String> articleIdList) {
        if (CollectionUtils.isEmpty(articleIdList)) {
            return new ArrayList<>();
        }
        return notEmptyList(mapper.listByArticleIdList(articleIdList));
    }

    @Override
    public int batchUpdate(List<Article> articleList) {
        return mapper.batchUpdate(articleList);
    }

    @Override
    public int logicDeleteById(Long id) {
        if (Objects.isNull(id)) {
            return 0;
        }
        Article condition = new Article();
        condition.setId(id);
        condition.setDelete(true);
        return mapper.updateByPrimaryKeySelective(condition);
    }

    @Override
    public void topArticle(String articleId) {
        if (Objects.isNull(articleId)) {
            return;
        }
        mapper.topArticle(EArticleSortStatusEnum.TOP.getCode(), articleId);
    }

    @Override
    public void increaseVisit(Long articleId) {
        mapper.increaseVisit(articleId);
    }
}

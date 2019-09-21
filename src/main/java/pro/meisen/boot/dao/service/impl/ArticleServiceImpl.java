package pro.meisen.boot.dao.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import pro.meisen.boot.dao.TagSearchParam;
import pro.meisen.boot.dao.service.basic.BasicServiceImpl;
import pro.meisen.boot.domain.Article;
import pro.meisen.boot.dao.mapper.ArticleMapper;
import pro.meisen.boot.dao.mapper.BasicMapper;
import pro.meisen.boot.dao.service.ArticleService;
import pro.meisen.boot.web.req.BlogSearchModel;
import pro.meisen.boot.web.req.TagSearchModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ArticleServiceImpl extends BasicServiceImpl<Article> implements ArticleService {

    @Autowired
    private ArticleMapper mapper;

    @Override
    public BasicMapper<Long, Article> getMapper() {
        return mapper;
    }

    @Override
    public Article findByArticleId(String articleId) {
        if (Strings.isEmpty(articleId)) {
            return null;
        }
        return mapper.findByArticleId(articleId);
    }

    @Override
    public List<Article> listAllArticle() {
        return notEmptyList(mapper.listAllArticles());
    }

    @Override
    public List<Article> listByCondition(Article article) {
        return notEmptyList(mapper.listByCondition(article));
    }

    @Override
    public Page<Article> listArticleWithPage(BlogSearchModel request) {
        if (Strings.isEmpty(request.getColumn())) {
            String column = Strings.isEmpty(request.getColumn()) ? "create_time" : request.getColumn();
            String order = Strings.isEmpty(request.getOrder()) ? "desc" : request.getOrder();
            request.setOrderBy(column + " " + order);
        }
        return PageHelper.startPage(request.getPageNum(), request.getPageSize(), request.getOrderBy())
                .doSelectPage(() -> mapper.listByPage(request));
    }

    @Override
    public List<Article> listByIds(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return new ArrayList<>();
        }
        return notEmptyList(mapper.listByIds(idList));
    }

    @Override
    public Page<Article> listByIdListWithPage(TagSearchParam param) {
        if (Objects.isNull(param) || CollectionUtils.isEmpty(param.getIdList())) {
            Page<Article> emptyPage = new Page<>();
            emptyPage.setTotal(0);
            return emptyPage;
        }
        if (Strings.isEmpty(param.getColumn())) {
            String column = Strings.isEmpty(param.getColumn()) ? "create_time" : param.getColumn();
            String order = Strings.isEmpty(param.getOrder()) ? "desc" : param.getOrder();
            param.setOrderBy(column + " " + order);
        }
        return PageHelper.startPage(param.getPageNum(), param.getPageSize(), param.getOrderBy())
                .doSelectPage(() -> mapper.listByIdListWithPage(param));
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
}

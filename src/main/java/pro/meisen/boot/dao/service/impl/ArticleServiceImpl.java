package pro.meisen.boot.dao.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import pro.meisen.boot.dao.service.basic.BasicServiceImpl;
import pro.meisen.boot.domain.Article;
import pro.meisen.boot.dao.mapper.ArticleMapper;
import pro.meisen.boot.dao.mapper.BasicMapper;
import pro.meisen.boot.dao.service.ArticleService;
import pro.meisen.boot.web.req.BlogSearchModel;

import java.util.ArrayList;
import java.util.List;

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
            String column = request.getColumn();
            String order = Strings.isEmpty(request.getOrder()) ? "" : request.getOrder();
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

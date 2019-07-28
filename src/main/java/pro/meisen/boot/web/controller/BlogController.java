package pro.meisen.boot.web.controller;

import com.github.pagehelper.Page;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pro.meisen.boot.core.exception.AppException;
import pro.meisen.boot.dao.service.ArticleService;
import pro.meisen.boot.dao.service.TagService;
import pro.meisen.boot.domain.Article;
import pro.meisen.boot.domain.common.ErrorCode;
import pro.meisen.boot.domain.helper.ArticleHelper;
import pro.meisen.boot.helper.SplitterHelper;
import pro.meisen.boot.uc.BlogManage;
import pro.meisen.boot.web.req.BlogSearchModel;
import pro.meisen.boot.web.req.PageModel;
import pro.meisen.boot.web.res.AchieveBlogVo;
import pro.meisen.boot.web.res.BlogVo;
import pro.meisen.boot.web.res.ResultPageData;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 *
 * @author meisen
 * 2019-05-23
 */
@RestController
@RequestMapping("/api/blog")
public class BlogController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlogController.class);

    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleHelper articleHelper;
    @Autowired
    private BlogManage blogManage;

    @GetMapping(value = "/all")
    public ResultPageData<BlogVo> all(HttpServletRequest request, Integer pageNum, Integer pageSize) {
        if (null == pageNum || pageSize == null) {
            pageNum = 0;
            pageSize = 10;
        }
        BlogSearchModel search = new BlogSearchModel();
        search.setPublish(1);
        search.setPageNum(pageNum);
        search.setPageSize(pageSize);
        Page<Article> articlePage = blogManage.listArticleWithPage(search);
        List<BlogVo> blogVoList = articleHelper.assembleBlogVo(articlePage.getResult());
        return new ResultPageData<>(blogVoList, articlePage.getTotal(), new PageModel(pageNum, pageSize));
    }

    @GetMapping(value = "/info")
    public BlogVo detail(HttpServletRequest request, @RequestParam("id") String id) {
        if (Strings.isEmpty(id)) {
            throw new AppException(ErrorCode.APP_ERROR_PARAM_ILLEGAL, "参数为空, 请确认输入");
        }
        Article article = blogManage.getDetailByArticleId(id);
        return articleHelper.assembleBlogVo(article);
    }

    @GetMapping(value = "/tag")
    public List<BlogVo> tagArticle(HttpServletRequest request, @RequestParam("tagName") String tagName) {
        if (Strings.isEmpty(tagName)) {
            throw new AppException(ErrorCode.APP_ERROR_PARAM_ILLEGAL, "参数为空, 请确认输入");
        }
        List<Article> articleList = blogManage.listByTagName(tagName);
        return articleHelper.assembleBlogVo(articleList);
    }

    // 归档文章
    @GetMapping(value = "/achieve")
    public Map<String, List<AchieveBlogVo>> achieve(HttpServletRequest request) {
        List<Article> articleList = articleService.listAllArticle();
        return achieveBlog(articleList);
    }

    /**
     * 归档文章
     * @return 文章
     */
    private Map<String, List<AchieveBlogVo>> achieveBlog(List<Article> articleList) {
        Map<String, List<Article>> blogMap = blogManage.achieveBlog(articleList);
        Map<String, List<AchieveBlogVo>> blogVoMap = new HashMap<>();
        for (Map.Entry<String, List<Article>> map : blogMap.entrySet()) {
            String key = map.getKey();
            List<AchieveBlogVo> blogVoList = new ArrayList<>();
            for (Article article : map.getValue()) {
                AchieveBlogVo blogVo = new AchieveBlogVo();
                blogVo.setArticleId(article.getArticleId());
                blogVo.setBlogDate(article.getCreateTime());
                blogVo.setTitle(article.getTitle());
                blogVoList.add(blogVo);
            }
            blogVoMap.put(key, blogVoList);
        }
        return blogVoMap;
    }

}

package pro.meisen.boot.web.controller;

import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pro.meisen.boot.core.exception.AppException;
import pro.meisen.boot.dao.service.ArticleService;
import pro.meisen.boot.domain.Article;
import pro.meisen.boot.domain.common.ErrorCode;
import pro.meisen.boot.domain.helper.ArticleHelper;
import pro.meisen.boot.uc.BlogManage;
import pro.meisen.boot.web.req.BlogSearchModel;
import pro.meisen.boot.web.req.PageModel;
import pro.meisen.boot.web.req.TagSearchModel;
import pro.meisen.boot.web.res.AchieveBlogVo;
import pro.meisen.boot.web.res.BlogVo;
import pro.meisen.boot.web.res.ResultPageData;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author meisen
 * 2019-05-23
 */
@RestController
@RequestMapping("/api/blog")
@Api(description = "前端文章相关接口", tags = "前端文章相关接口tag")
public class BlogController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlogController.class);

    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleHelper articleHelper;
    @Autowired
    private BlogManage blogManage;

    @GetMapping(value = "/all")
    @ApiOperation(notes = "查询所有的文章", value = "查询所有的文章")
    public ResultPageData<BlogVo> all(Integer pageNum, Integer pageSize, String column, String order) {
        if (null == pageNum || pageSize == null) {
            pageNum = 0;
            pageSize = 10;
        }
        BlogSearchModel search = new BlogSearchModel();
        search.setPublish(1);
        search.setPageNum(pageNum);
        search.setPageSize(pageSize);
        search.setColumn(column);
        search.setOrder(order);
        ResultPageData<Article> articlePage = blogManage.listArticleWithPage(search);
        List<BlogVo> blogVoList = articleHelper.assembleBlogVo(articlePage.getData());
        return new ResultPageData<>(blogVoList, articlePage.getCount(), new PageModel(pageNum, pageSize));
    }

    @GetMapping(value = "/info")
    @ApiOperation(notes = "获取文章详情", value = "获取文章详情")
    public BlogVo detail(HttpServletRequest request, @RequestParam("id") String id) {
        if (Strings.isEmpty(id)) {
            throw new AppException(ErrorCode.APP_ERROR_PARAM_ILLEGAL, "参数为空, 请确认输入");
        }
        Article article = blogManage.getDetailByArticleIdWithCache(id);
        return articleHelper.assembleBlogVo(article);
    }

    @GetMapping(value = "/tag")
    @ApiOperation(notes = "获取标签相关文章", value = "获取标签相关文章")
    public ResultPageData<BlogVo> tagArticle(HttpServletRequest request, @ModelAttribute TagSearchModel searchModel) {
        if (Objects.isNull(searchModel) || Strings.isEmpty(searchModel.getTagName())) {
            throw new AppException(ErrorCode.APP_ERROR_PARAM_ILLEGAL, "参数为空, 请确认输入");
        }
        ResultPageData<Article> pageData = blogManage.listByTagName(searchModel);
        List<BlogVo> blogVoList = articleHelper.assembleBlogVo(pageData.getData());
        return new ResultPageData<>(blogVoList, pageData.getCount(), searchModel);
    }

    // 归档文章
    @GetMapping(value = "/achieve")
    @ApiOperation(notes = "获取归档文章", value = "获取归档文章")
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
        // 排序
        for (Map.Entry<String, List<AchieveBlogVo>> entry : blogVoMap.entrySet()) {
            List<AchieveBlogVo> achieveBlogVoList = entry.getValue();
            List<AchieveBlogVo> sortBlogVoList = achieveBlogVoList.stream()
                    .sorted(Comparator.comparing(AchieveBlogVo::getBlogDate).reversed())
                    .collect(Collectors.toList());
            entry.setValue(sortBlogVoList);
        }
        return blogVoMap;
    }

}

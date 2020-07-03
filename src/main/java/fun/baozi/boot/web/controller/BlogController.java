package fun.baozi.boot.web.controller;

import com.google.common.collect.Lists;
import fun.baozi.boot.core.exception.AppException;
import fun.baozi.boot.dao.service.ArticleService;
import fun.baozi.boot.domain.Article;
import fun.baozi.boot.domain.common.ErrorCode;
import fun.baozi.boot.domain.enums.EArticlePublishStatusEnum;
import fun.baozi.boot.domain.helper.ArticleHelper;
import fun.baozi.boot.uc.BlogManage;
import fun.baozi.boot.web.req.BlogSearchForm;
import fun.baozi.boot.web.req.PageInfo;
import fun.baozi.boot.web.req.TagSearchForm;
import fun.baozi.boot.web.res.AchieveBlogVo;
import fun.baozi.boot.web.res.BlogVo;
import fun.baozi.boot.web.res.PageData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author baozi
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

    @ApiOperation(notes = "查询所有的文章", value = "查询所有的文章")
    @GetMapping(value = "/all")
    public PageData<BlogVo> all(@ModelAttribute BlogSearchForm form) {
        if (Objects.isNull(form.getPageNum()) || Objects.isNull(form.getPageSize())) {
            form.setPageNum(0);
            form.setPageSize(10);
        }
        form.setPublish(1);
        PageData<Article> articlePage = blogManage.listArticleWithPage(form);
        List<BlogVo> blogVoList = articleHelper.assembleBlogVo(articlePage.getData());
        return new PageData<>(blogVoList, articlePage.getCount(), form);
    }

    @ApiOperation(notes = "根据条件查询文章", value = "根据条件查询文章")
    @GetMapping(value = "/search")
    public PageData<BlogVo> search(@RequestParam("text") String text, @RequestParam("pageNum") Integer pageNum
            , @RequestParam("pageSize") Integer pageSize) {
        PageInfo pageInfo = new PageInfo();
        if (Objects.isNull(pageNum) || Objects.isNull(pageSize) || Strings.isBlank(text) || text.length() < 2) {
            pageInfo.setPageNum(0);
            pageInfo.setPageSize(10);
            return new PageData<BlogVo>(Lists.newArrayList(), 0L, pageInfo);
        }
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        PageData<Article> pageData = blogManage.searchEverything(text, pageNum, pageSize);
        List<BlogVo> blogVoList = articleHelper.assembleBlogVo(pageData.getData());
        return new PageData<>(blogVoList, pageData.getCount(), pageInfo);
    }

    @ApiOperation(notes = "获取文章详情", value = "获取文章详情")
    @GetMapping(value = "/info")
    public BlogVo detail(HttpServletRequest request, @RequestParam("id") String id) {
        if (Strings.isEmpty(id)) {
            throw new AppException(ErrorCode.APP_ERROR_PARAM_ILLEGAL, "参数为空, 请确认输入");
        }
        Article article = blogManage.getDetailByArticleIdWithIncrVisit(id);
        return articleHelper.assembleBlogVo(article);
    }

    @ApiOperation(notes = "获取标签相关文章", value = "获取标签相关文章")
    @GetMapping(value = "/tag")
    public PageData<BlogVo> tagArticle(HttpServletRequest request, @ModelAttribute TagSearchForm form) {
        if (Objects.isNull(form) || Strings.isEmpty(form.getTagName())) {
            throw new AppException(ErrorCode.APP_ERROR_PARAM_ILLEGAL, "参数为空, 请确认输入");
        }
        PageData<Article> pageData = blogManage.listByTagName(form);
        List<BlogVo> blogVoList = articleHelper.assembleBlogVo(pageData.getData());
        return new PageData<>(blogVoList, pageData.getCount(), form);
    }

    // 归档文章
    @GetMapping(value = "/achieve")
    @ApiOperation(notes = "获取归档文章", value = "获取归档文章")
    public Map<String, List<AchieveBlogVo>> achieve(HttpServletRequest request) {
        Article condition = new Article();
        condition.setPublish(EArticlePublishStatusEnum.PUBLISH.isCode());

        List<Article> articleList = articleService.listByCondition(condition);
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

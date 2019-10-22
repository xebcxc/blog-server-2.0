package pro.meisen.boot.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pro.meisen.boot.core.exception.AppException;
import pro.meisen.boot.dao.service.ArticleService;
import pro.meisen.boot.domain.Article;
import pro.meisen.boot.domain.common.ErrorCode;
import pro.meisen.boot.domain.helper.ArticleHelper;
import pro.meisen.boot.uc.BlogManage;
import pro.meisen.boot.web.req.BlogSearchForm;
import pro.meisen.boot.web.req.PageInfo;
import pro.meisen.boot.web.res.BlogVo;
import pro.meisen.boot.web.res.PageData;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * @author meisen
 * 2019-07-27
 */
@RestController
@RequestMapping("/api/articles")
@Api(description = "后台文章相关接口", tags = "后台文章相关接口tag")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private BlogManage blogManage;
    @Autowired
    private ArticleHelper articleHelper;

    @GetMapping(value = "/info")
    @ApiOperation(value = "获取文章详情,需要授权", notes = "获取文章详情,需要授权")
    public BlogVo detail(HttpServletRequest request, @RequestParam("id") String id) {
        if (Strings.isEmpty(id)) {
            throw new AppException(ErrorCode.APP_ERROR_PARAM_ILLEGAL, "参数为空, 请确认输入");
        }
        Article article = blogManage.getDetailByArticleId(id);
        return articleHelper.assembleBlogVo(article);
    }

    @GetMapping(value = "/condition")
    @ApiOperation(value = "获取文章列表,需要授权", notes = "获取文章列表,需要授权")
    public PageData<BlogVo> condition(@ModelAttribute BlogSearchForm form) {
        if (Objects.isNull(form.getPageNum()) || Objects.isNull(form.getPageSize())) {
            form.setPageNum(0);
            form.setPageSize(10);
        }
        PageData<Article> articlePage = blogManage.listArticleWithPage(form);
        List<BlogVo> blogVoList = articleHelper.assembleBlogVo(articlePage.getData());
        return new PageData<>(blogVoList, articlePage.getCount(), form);
    }

    @PostMapping(value = "/add")
    @ApiOperation(value = "后台新增文章", notes = "后台新增文章")
    public Boolean addArticle(HttpServletRequest request, @RequestBody Article article) {
        if (null == article) {
            throw new AppException(ErrorCode.APP_ERROR_PARAM_ILLEGAL, "参数为空, 请确认输入");
        }
        // 校验参数
        validateAddArticleParams(article);
        blogManage.addArticle(article);
        return true;
    }

    @PutMapping(value = "/top")
    @ApiOperation(value = "置顶文章", notes = "置顶文章")
    public Boolean topArticle(@RequestParam("articleId") Long articleId) {
        if (Objects.isNull(articleId)) {
            throw new AppException(ErrorCode.APP_ERROR_PARAM_ILLEGAL, "参数为空, 请确认输入");
        }
        articleService.topArticle(articleId);
        return true;
    }

    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "删除文章", notes = "删除文章")
    public Boolean deleteArticle(HttpServletRequest request, @RequestParam("id")String articleId) {
        if (Strings.isEmpty(articleId)) {
            throw new AppException(ErrorCode.APP_ERROR_PARAM_ILLEGAL, "文章id为空,请刷新后重试");
        }
        // 删除文章
        blogManage.deleteArticleByArticleId(articleId);
        return true;
    }

    @PutMapping("/publish")
    @ApiOperation(value = "发布文章", notes = "发布文章")
    public Boolean publishArticle(HttpServletRequest request, @RequestBody Article article) {
        if (null == article || null == article.getId()) {
            throw new AppException(ErrorCode.APP_ERROR_PARAM_ILLEGAL, "发布文章为空,请刷新后重试");
        }
        Long id = article.getId();
        Boolean publish = article.getPublish();
        Article updateArticle = new Article();
        updateArticle.setId(id);
        updateArticle.setPublish(publish);
        articleService.updateByPrimaryKeySelective(updateArticle);
        return true;
    }

    @PutMapping("/update")
    @ApiOperation(value = "更新文章", notes = "更新文章")
    public Article updateArticle(HttpServletRequest request, @RequestBody Article article) {
        if (null == article || Strings.isEmpty(article.getArticleId())) {
            throw new AppException(ErrorCode.APP_ERROR_PARAM_ILLEGAL, "发布文章为空,请刷新后重试");
        }
        String articleId = article.getArticleId();
        Article originArticle = articleService.findByArticleId(articleId);
        if (null == originArticle) {
            throw new AppException(ErrorCode.APP_ERROR_PARAM_ILLEGAL, "文章不存在,请刷新后重试");
        }
        article.setId(originArticle.getId());
        blogManage.updateArticle(article);
        return article;
    }


    /**
     * 校验参数
     * @param article 文章参数
     */
    private void validateAddArticleParams(Article article) {
        if (Strings.isEmpty(article.getTitle())) {
            throw new AppException(ErrorCode.APP_ERROR_PARAM_ILLEGAL, "文章标题不能为空, 请确认输入");
        } else if (Strings.isEmpty(article.getIntroduce())) {
            throw new AppException(ErrorCode.APP_ERROR_PARAM_ILLEGAL, "文章简介不能为空, 请确认输入");
        } else if (Strings.isEmpty(article.getContent())) {
            throw new AppException(ErrorCode.APP_ERROR_PARAM_ILLEGAL, "文章内容不能为空, 请确认输入");
        } else if (Strings.isEmpty(article.getThumb())) {
            throw new AppException(ErrorCode.APP_ERROR_PARAM_ILLEGAL, "文章图片不能为空, 请确认输入");
        }
        // 目前topic设置默认值
        if (Strings.isEmpty(article.getTopic())) {
            article.setTopic("");
        }

    }


}

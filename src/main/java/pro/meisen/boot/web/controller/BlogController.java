package pro.meisen.boot.web.controller;

import com.github.pagehelper.Page;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import pro.meisen.boot.core.constants.AppConstants;
import pro.meisen.boot.core.exception.AppException;
import pro.meisen.boot.dao.service.ArticleService;
import pro.meisen.boot.dao.service.TagService;
import pro.meisen.boot.domain.Article;
import pro.meisen.boot.domain.common.ErrorCode;
import pro.meisen.boot.domain.Tag;
import pro.meisen.boot.helper.SplitterHelper;
import pro.meisen.boot.uc.BlogManage;
import pro.meisen.boot.web.req.BlogSearchModel;
import pro.meisen.boot.web.req.PageModel;
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
public class BlogController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlogController.class);

    @Autowired
    private ArticleService articleService;
    @Autowired
    private SplitterHelper splitterHelper;
    @Autowired
    private TagService tagService;
    @Autowired
    private BlogManage blogManage;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResultPageData<BlogVo> all(HttpServletRequest request, Integer pageNum, Integer pageSize) {
        if (null == pageNum || pageSize == null) {
            pageNum = 0;
            pageSize = 6;
        }
        BlogSearchModel search = new BlogSearchModel();
        search.setPublish(1);
        search.setPageNum(pageNum);
        search.setPageSize(pageSize);
        Page<Article> articlePage = blogManage.listArticleWithPage(search);
        List<BlogVo> blogVoList = assembleBlogRs(articlePage.getResult());
        return new ResultPageData<>(blogVoList, articlePage.getTotal(), new PageModel(pageNum, pageSize));
    }

    @GetMapping(value = "/info")
    public BlogVo detail(HttpServletRequest request, @RequestParam("id") String id) {
        if (Strings.isEmpty(id)) {
            throw new AppException(ErrorCode.APP_ERROR_PARAM_ILLEGAL, "参数为空, 请确认输入");
        }
        Article article = blogManage.getDetailByArticleId(id);
        return assembleBlogRs(article);
    }

    @RequestMapping(value = "/tag", method = RequestMethod.GET)
    public List<BlogVo> tagArticle(HttpServletRequest request, @RequestParam("tagName") String tagName) {
        if (Strings.isEmpty(tagName)) {
            throw new AppException(ErrorCode.APP_ERROR_PARAM_ILLEGAL, "参数为空, 请确认输入");
        }
        List<Article> articleList = blogManage.listByTagName(tagName);
        return assembleBlogRs(articleList);
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Boolean addArticle(HttpServletRequest request, @RequestBody Article article) {
        if (null == article) {
            throw new AppException(ErrorCode.APP_ERROR_PARAM_ILLEGAL, "参数为空, 请确认输入");
        }
        // 校验参数
        validateAddArticleParams(article);
        blogManage.addArticle(article);
        return true;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Boolean deleteArticle(HttpServletRequest request, @RequestParam("id")Long id) {
        if (null == id) {
            throw new AppException(ErrorCode.APP_ERROR_PARAM_ILLEGAL, "文章id为空,请刷新后重试");
        }
        // 删除文章
        blogManage.deleteArticleById(id);
        return true;
    }

    @PutMapping("/increaseVisit")
    public Boolean increaseVisit(HttpServletRequest request, @RequestBody Article blog) {
        if (null == blog || Strings.isEmpty(blog.getArticleId())) {
            throw new AppException(ErrorCode.APP_ERROR_PARAM_ILLEGAL, "文章id为空,请刷新后重试");
        }
        Article article = blogManage.getDetailByArticleId(blog.getArticleId());
        if (null == article) {
            throw new AppException(ErrorCode.APP_ERROR_PARAM_ILLEGAL, "文章不存在,请刷新后重试");
        }
        Integer visit = article.getVisit();
        Article updateArticle = new Article();
        updateArticle.setId(article.getId());
        updateArticle.setVisit(++visit);
        articleService.update(updateArticle);
        return true;
    }


    // 归档文章
    @RequestMapping(value = "/achieve", method = RequestMethod.GET)
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
    }

    /**
     * 组装BlogRs
     * @param articleList 数据库ArticleList
     * @return 返回到页面上的BlogRs
     */
    private List<BlogVo> assembleBlogRs(List<Article> articleList) {
        if (CollectionUtils.isEmpty(articleList)) {
            return new ArrayList<>();
        }
        List<BlogVo> rsList = new ArrayList<>();
        for (Article article : articleList) {
            if (article == null) {
                continue;
            }
            BlogVo rs = assembleBlogRs(article);
            rsList.add(rs);
        }
        return rsList;
    }

    private BlogVo assembleBlogRs(Article article) {
        BlogVo rs = new BlogVo();
        rs.setArticleId(article.getArticleId());
        rs.setThumb(article.getThumb());
        rs.setTitle(article.getTitle());
        rs.setContent(article.getContent());
        rs.setTopic(article.getTopic());
        rs.setIntroduce(article.getIntroduce());
        rs.setVisit(article.getVisit());
        rs.setCompliment(article.getCompliment());
        rs.setCreateTime(article.getCreateTime());
        rs.setModifyTime(article.getModifyTime());
        if (Strings.isNotBlank(article.getTags())) {
            List<Long> tagList = splitterHelper.splitToLongList(article.getTags(), AppConstants.COMMON_SPLIT);
            List<String> tagNameList =  tagService.listByIds(tagList).stream().map(Tag::getTagName).collect(Collectors.toList());
            rs.setTagList(tagNameList);
        }
        return rs;
    }


}

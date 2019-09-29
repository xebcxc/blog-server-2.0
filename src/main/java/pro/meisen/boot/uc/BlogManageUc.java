package pro.meisen.boot.uc;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import pro.meisen.boot.core.constants.AppConstants;
import pro.meisen.boot.core.exception.AppException;
import pro.meisen.boot.dao.TagSearchParam;
import pro.meisen.boot.dao.service.ArticleService;
import pro.meisen.boot.dao.service.TagService;
import pro.meisen.boot.domain.Article;
import pro.meisen.boot.domain.Tag;
import pro.meisen.boot.domain.common.ErrorCode;
import pro.meisen.boot.ext.redis.RedisKey;
import pro.meisen.boot.ext.redis.RedisOperation;
import pro.meisen.boot.helper.SplitterHelper;
import pro.meisen.boot.helper.StringHelper;
import pro.meisen.boot.web.req.BlogSearchForm;
import pro.meisen.boot.web.req.TagSearchForm;
import pro.meisen.boot.web.res.PageData;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author meisen
 * 2019-05-23
 */
@Component
public class BlogManageUc implements BlogManage{

    private static final Logger LOGGER = LoggerFactory.getLogger(BlogManageUc.class);

    @Autowired
    private ArticleService articleService;
    @Autowired
    private TagService tagService;
    @Autowired
    private SplitterHelper splitterHelper;
    @Autowired
    private StringHelper stringHelper;
    @Autowired
    private RedisOperation<String> redisOperation;

//    @DataCache(key = "page_blog_")
    @Override
    public PageData<Article> listArticleWithPage(BlogSearchForm request) {
        return articleService.listArticleWithPage(request);
    }

    @Override
//    @DataCache(key = "blog_detail_")
    public Article getDetailByArticleIdWithIncrVisit(String articleId) {
        Article article = articleService.findByArticleId(articleId);
        if (null == article) {
            throw new AppException(ErrorCode.APP_ERROR_PARAM_ILLEGAL, "文章不存在,请确认参数");
        }
        Long id = article.getId();
        String member = id.toString();
        redisOperation.zIncr(RedisKey.ARTICLE_INFO.getKey(), member, 1);
        return article;
    }
    @Override
    public Article getDetailByArticleId(String articleId) {
        Article article = articleService.findByArticleId(articleId);
        if (null == article) {
            throw new AppException(ErrorCode.APP_ERROR_PARAM_ILLEGAL, "文章不存在,请确认参数");
        }
        return article;
    }

    @Override
//    @DataCache(key = "blog_list_")
    public PageData<Article> listByTagName(TagSearchForm searchModel) {
        Tag tag = tagService.getByTagName(searchModel.getTagName());
        PageData<Article> pageData = new PageData<>();
        if (tag != null && Strings.isNotEmpty(tag.getArticleIds())) {
            List<Long> idList = splitterHelper.splitToLongList(tag.getArticleIds(), AppConstants.COMMON_SPLIT);
            TagSearchParam param = new TagSearchParam();
            BeanUtils.copyProperties(searchModel, param);
            param.setIdList(idList);
            pageData = articleService.listByIdListWithPage(param);
        } else {
            pageData.setCount(0L);
            pageData.setData(new ArrayList<>());
        }
        return pageData;
    }


    @Override
//    @DataCache(key ="blog_add", type = DataCacheType.INSERT)
    @Transactional(rollbackOn = Exception.class)
    public void addArticle(Article article) {
        try {
            // 设置一些默认值
            updateAddArticle(article);
            // 先保存文章
            articleService.insertSelective(article);
            Long id = article.getId();
            // 如果tags不为空 ,需要保存tag信息
            if (Strings.isNotEmpty(article.getTags())) {
                saveOrUpdateTags(article, id);
            }
        } catch (Exception e) {
            LOGGER.error("添加文章失败, article: {}|error={}", article, e.getMessage());
            throw e;
        }

    }

    @Override
//    @DataCache(key ="blog_delete", type = DataCacheType.DELETE)
    @Transactional(rollbackOn = Exception.class)
    public void deleteArticleById(Long id) {
        Article article = articleService.findById(id);
        if (article == null) {
            throw new AppException(ErrorCode.APP_ERROR_PARAM_ILLEGAL, "文章不存在,请刷新后重试");
        }
        try {
            articleService.logicDeleteById(article.getId());
            String tags = article.getTags();
            List<Long> tagIdList = splitterHelper.splitToLongList(tags, AppConstants.COMMON_SPLIT);
            List<Tag> tagList = tagService.listByIds(tagIdList);
            for (Tag tag : tagList) {
                String articleIds = tag.getArticleIds();
                articleIds = stringHelper.wipeSpecifyStr(articleIds, String.valueOf(article.getId()));
                tag.setArticleIds(articleIds);
            }
            tagService.batchUpdate(tagList);
        } catch (Exception e) {
            LOGGER.error("删除文章失败, article: {}|errorMsg={}", article, e.getMessage());
            throw e;
        }
    }

    @Override
//    @DataCache(key ="blog_delete", type = DataCacheType.DELETE)
    @Transactional(rollbackOn = Exception.class)
    public void deleteArticleByArticleId(String articleId) {
        Article article = articleService.findByArticleId(articleId);
        if (article == null) {
            throw new AppException(ErrorCode.APP_ERROR_PARAM_ILLEGAL, "文章不存在,请刷新后重试");
        }
        try {
            deleteArticleById(article.getId());
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
//    @DataCache(key ="blog_achieve")
    public Map<String, List<Article>> achieveBlog(List<Article> articleList) {
        Map<String, List<Article>> blogVoMap = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        for (Article article : articleList) {
            Date createTime = article.getCreateTime();
            calendar.setTime(createTime);
            String year = String.valueOf(calendar.get(Calendar.YEAR));
            List<Article> voList = blogVoMap.get(year);
            if (CollectionUtils.isEmpty(voList)) {
                List<Article> articles = new ArrayList<>();
                articles.add(article);
                blogVoMap.put(year, articles);
            } else {
                voList.add(article);
                blogVoMap.put(year, voList);
            }
        }
        return blogVoMap;
    }

    @Override
    public Article updateArticle(Article article) {
        articleService.updateByPrimaryKeySelective(article);
        // 如果tags不为空 ,需要保存tag信息
        if (Strings.isNotEmpty(article.getTags())) {
            saveOrUpdateTags(article, article.getId());
        }
        return article;
    }

    /**
     * 保存tag信息
     * @param article 文章
     * @param id 文章id
     */
    private void saveOrUpdateTags(Article article, Long id) {
        // 分割获取tagNameList
        List<String> tagNameList = splitterHelper.splitToStringList(article.getTags(), AppConstants.COMMON_SPLIT);
        // 根据tagName获取所有的Tag
        List<Tag> tagList = tagService.listByTagNameList(tagNameList);
        Map<String, Tag> existTag = new HashMap<>();
        // 存在的TagList
        for (Tag tag : tagList) {
            String tagName = tag.getTagName();
            if (stringHelper.isNumeric(tagName)) {
                continue;
            }
            existTag.put(tag.getTagName(), tag);
        }
        // 新增或更新tag信息 批量更新
        Set<Long> tagIdList = insertAndUpdateTagList(id, tagNameList, existTag);
        // 在保存了Tag对应的信息的更新Article
        updateArticleAfterSaveTag(article, tagIdList);
    }

    /**
     * 在保存了Tag对应的信息的更新Article
     * @param article 需要更新的文章
     * @param tagIdList 需要保存的tag id集合
     */
    private void updateArticleAfterSaveTag(Article article, Set<Long> tagIdList) {
        String tags = tagIdList.stream().map(String::valueOf).collect(Collectors.joining(","));
        Article condition = new Article();
        condition.setId(article.getId());
        condition.setTags(tags);
        articleService.updateByPrimaryKeySelective(condition);
    }

    /**
     * 过滤tag
     * 将新旧tag对应的Articled保存起来
     * @param id 需要保存的id
     * @param tagNameList 新增的tag名字list
     * @param existTagMap 已经存在tag
     * @return  Set<Long> 保存或更新后的所有Id 集合
     */
    private Set<Long> insertAndUpdateTagList(Long id, List<String> tagNameList, Map<String, Tag> existTagMap) {
        List<Tag> updateTagList = new ArrayList<>();
        List<Tag> insertTagList = new ArrayList<>();
        tagNameList.stream()
                .filter(tagName -> !stringHelper.isNumeric(tagName))
                .forEach(tagName -> {
                    // 判断是否有tag
                    Tag tag = existTagMap.get(tagName);
                    // 当前tag已经存在
                    if (null != tag) {
                        String articleIds = tag.getArticleIds();
                        if (!articleIds.contains(String.valueOf(id))) {
                            articleIds = articleIds + "," + id;
                            tag.setArticleIds(articleIds);
                            updateTagList.add(tag);
                        } else {
                            tag.setArticleIds(articleIds);
                            updateTagList.add(tag);
                        }
                    } else {
                        // 如果当前tag不存在
                        tag = new Tag();
                        tag.setTagName(tagName);
                        tag.setArticleIds(String.valueOf(id));
                        insertTagList.add(tag);
                    }
                });
        tagService.batchInsert(insertTagList);
        tagService.batchUpdate(updateTagList);

        Set<Long> idList = new HashSet<>();
        insertTagList.forEach(tag -> idList.add(tag.getId()));
        updateTagList.forEach(tag -> idList.add(tag.getId()));
        return idList;
    }

    /**
     * 更新添加文章的默认值
     * @param article 需要新增的文章
     */
    private void updateAddArticle(Article article) {
        if (null == article.getVisit()) {
            article.setVisit(0);
        }
        if (null == article.getCompliment()) {
            article.setCompliment(0);
        }
        if (null == article.getPublish()) {
            article.setPublish(false);
        }
        // 文章id为UUid
        article.setArticleId(UUID.randomUUID().toString().replaceAll("-", ""));
    }
}

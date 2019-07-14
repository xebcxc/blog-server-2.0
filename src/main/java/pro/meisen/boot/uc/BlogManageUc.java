package pro.meisen.boot.uc;

import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pro.meisen.boot.core.constants.AppConstants;
import pro.meisen.boot.dao.service.ArticleService;
import pro.meisen.boot.dao.service.TagService;
import pro.meisen.boot.domain.Article;
import pro.meisen.boot.domain.Tag;
import pro.meisen.boot.helper.SplitterHelper;
import pro.meisen.boot.helper.StringHelper;

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

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void addArticle(Article article) {
        try {
            // 设置一些默认值
            updateAddArticle(article);
            // 先保存文章
            articleService.save(article);
            Long id = article.getId();
            // 如果tags不为空 ,需要保存tag信息
            if (Strings.isNotEmpty(article.getTags())) {
                saveTags(article, id);
            }
        } catch (Exception e) {
            LOGGER.error("添加文章失败, article: {}|error={}", article, e.getMessage());
            throw e;
        }

    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void deleteArticle(Article article) {
        try {
            articleService.deleteById(article.getId());
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

    /**
     * 保存tag信息
     * @param article 文章
     * @param id 文章id
     */
    private void saveTags(Article article, Long id) {
        List<String> tagNameList = splitterHelper.splitToStringList(article.getTags(), AppConstants.COMMON_SPLIT);
        List<Tag> tagList = tagService.listByTagNameList(tagNameList);
        Map<String, Tag> existTag = new HashMap<>();
        for (Tag tag : tagList) {
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
        article.setTags(tags);
        articleService.update(article);
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
        tagNameList.forEach(tagName -> {
            Tag tag = existTagMap.get(tagName);
            // 当前tag已经存在
            if (null != tag) {
                String articleIds = tag.getArticleIds();
                if (!articleIds.contains(String.valueOf(id))) {
                    articleIds = articleIds + "," + id;
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
        article.setArticleId(UUID.randomUUID().toString());
    }
}

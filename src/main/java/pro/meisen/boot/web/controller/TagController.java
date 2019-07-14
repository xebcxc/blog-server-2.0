package pro.meisen.boot.web.controller;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.meisen.boot.core.constants.AppConstants;
import pro.meisen.boot.core.exception.AppException;
import pro.meisen.boot.dao.service.ArticleService;
import pro.meisen.boot.dao.service.TagService;
import pro.meisen.boot.domain.Article;
import pro.meisen.boot.domain.ErrorCode;
import pro.meisen.boot.domain.Tag;
import pro.meisen.boot.helper.SplitterHelper;
import pro.meisen.boot.helper.StringHelper;
import pro.meisen.boot.web.res.TagRs;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author meisen
 * 2019-07-14
 */
@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private SplitterHelper splitterHelper;
    @Autowired
    private StringHelper stringHelper;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<TagRs> all(HttpServletRequest request) {
        List<Tag> tagList = tagService.listAll();
        return assembleTagRs(tagList);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Boolean delete(HttpServletRequest request, @RequestParam("tagId") Long id) {
        if (null == id) {
            throw new AppException(ErrorCode.PARAM_ERROR, "参数错误,删除失败");
        }
        Tag tag = tagService.findById(id);
        if (tag == null) {
            throw new AppException(ErrorCode.PARAM_ERROR, "标签不存在,删除失败");
        }
        String articleIds = tag.getArticleIds();
        List<String> idList = splitterHelper.splitToStringList(articleIds, AppConstants.COMMON_SPLIT);
        List<Article> articleList = articleService.listByArticleIdList(idList);
        for (Article article : articleList) {
            String tags = article.getTags();
            if (Strings.isNotEmpty(tags)) {
                tags = stringHelper.wipeSpecifyStr(tags, String.valueOf(id));
                article.setTags(tags);
            }
        }
        articleService.batchUpdate(articleList);
        return true;
    }

    private List<TagRs> assembleTagRs(List<Tag> tagList) {
        if (CollectionUtils.isEmpty(tagList)) {
            return new ArrayList<>();
        }
        List<TagRs> tagRsList = new ArrayList<>();
        for (Tag tag : tagList) {
            TagRs rs = new TagRs();
            rs.setId(tag.getId());
            rs.setTagName(tag.getTagName());
            tagRsList.add(rs);
        }
        return tagRsList;
    }
}


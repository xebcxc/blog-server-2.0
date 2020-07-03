package fun.baozi.boot.domain.helper;

import fun.baozi.boot.core.constants.AppConstants;
import fun.baozi.boot.domain.Article;
import fun.baozi.boot.domain.Tag;
import fun.baozi.boot.helper.SplitterHelper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import fun.baozi.boot.dao.service.TagService;
import fun.baozi.boot.web.res.BlogVo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author baozi
 * 2019-07-27
 */
@Component
public class ArticleHelper {


    @Autowired
    private TagService tagService;
    @Autowired
    private SplitterHelper splitterHelper;

    /**
     * 组装BlogRs
     * @param articleList 数据库ArticleList
     * @return 返回到页面上的BlogRs
     */
    public List<BlogVo> assembleBlogVo(List<Article> articleList) {
        if (CollectionUtils.isEmpty(articleList)) {
            return new ArrayList<>();
        }
        List<BlogVo> rsList = new ArrayList<>();
        for (Article article : articleList) {
            if (article == null) {
                continue;
            }
            BlogVo rs = assembleBlogVo(article);
            rsList.add(rs);
        }
        return rsList;
    }

    public BlogVo assembleBlogVo(Article article) {
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
        rs.setPublish(article.getPublish());
        rs.setSort(article.getSort());
        if (Strings.isNotBlank(article.getTags())) {
            List<Long> tagList = splitterHelper.splitToLongList(article.getTags(), AppConstants.COMMON_SPLIT);
            List<String> tagNameList =  tagService.listByIds(tagList).stream().map(Tag::getTagName).collect(Collectors.toList());
            rs.setTagList(tagNameList);
        }
        return rs;
    }
}

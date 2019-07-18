package pro.meisen.boot.domain;

import pro.meisen.boot.domain.common.CommonDomain;

/**
 * @author meisen
 * 2019-07-13
 */
public class Tag extends CommonDomain {

    // 标签的名字
    private String tagName;
    // 包含改标签的所有文章id, 通过","分割
    private String articleIds;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getArticleIds() {
        return articleIds;
    }

    public void setArticleIds(String articleIds) {
        this.articleIds = articleIds;
    }
}

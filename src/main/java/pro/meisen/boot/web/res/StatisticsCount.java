package pro.meisen.boot.web.res;

/**
 * 统计数据
 * @author meisen
 * 2019-07-27
 */
public class StatisticsCount {

    // 文章总数
    private Integer articleCount;
    // 已发布文章
    private Integer publishCount;
    // 未发布文章
    private Integer nonPublishCount;

    public Integer getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(Integer articleCount) {
        this.articleCount = articleCount;
    }

    public Integer getPublishCount() {
        return publishCount;
    }

    public void setPublishCount(Integer publishCount) {
        this.publishCount = publishCount;
    }

    public Integer getNonPublishCount() {
        return nonPublishCount;
    }

    public void setNonPublishCount(Integer nonPublishCount) {
        this.nonPublishCount = nonPublishCount;
    }
}

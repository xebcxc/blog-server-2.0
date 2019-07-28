package pro.meisen.boot.web.res;

/**
 * 统计数据
 * @author meisen
 * 2019-07-27
 */
public class StatisticsCount {

    // 文章总数
    private Long articleCount;
    // 已发布文章
    private Long publishCount;
    // 未发布文章
    private Long nonPublishCount;

    public Long getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(Long articleCount) {
        this.articleCount = articleCount;
    }

    public Long getPublishCount() {
        return publishCount;
    }

    public void setPublishCount(Long publishCount) {
        this.publishCount = publishCount;
    }

    public Long getNonPublishCount() {
        return nonPublishCount;
    }

    public void setNonPublishCount(Long nonPublishCount) {
        this.nonPublishCount = nonPublishCount;
    }
}

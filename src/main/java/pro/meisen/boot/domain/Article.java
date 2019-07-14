package pro.meisen.boot.domain;

import java.io.Serializable;

/**
 * 相关的文章
 * @author meisen
 * 2019-05-23
 */
public class Article extends CommonDomain implements Serializable {

    private static final long serialVersionUID = -4137589459810896056L;
    // 文章的id, 页面url通过这个来请求, UUID
    private String articleId;
    // 图片
    private String thumb;
    // 标题
    private String title;
    // 内容
    private String content;
    // 简介
    private String introduce;
    // 标签  多个标签通过,分割
    private String tags;
    // 访问量
    private Integer visit;
    // 点赞数
    private Integer compliment;
    // 是否发布
    private Boolean publish;


    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb == null ? null : thumb.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags == null ? null : tags.trim();
    }

    public Integer getVisit() {
        return visit;
    }

    public void setVisit(Integer visit) {
        this.visit = visit;
    }

    public Integer getCompliment() {
        return compliment;
    }

    public void setCompliment(Integer compliment) {
        this.compliment = compliment;
    }

    public Boolean getPublish() {
        return publish;
    }

    public void setPublish(Boolean publish) {
        this.publish = publish;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }
}
package pro.meisen.boot.web.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * @author meisen
 * 2019-05-23
 */
@ApiModel
public class BlogVo {
    @ApiModelProperty("文章id")
    private String articleId;
    @ApiModelProperty("图片")
    private String thumb;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("主题")
    private String topic;
    @ApiModelProperty("内容")
    private String content;
    @ApiModelProperty("简介")
    private String introduce;
    @ApiModelProperty("标签")
    private List<String> tagList;
    @ApiModelProperty("访问量")
    private Integer visit;
    @ApiModelProperty("发布")
    private boolean publish;
    @ApiModelProperty("点赞数")
    private Integer compliment;
    private Date createTime;

    private Date modifyTime;

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
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

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public boolean isPublish() {
        return publish;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
    }
}

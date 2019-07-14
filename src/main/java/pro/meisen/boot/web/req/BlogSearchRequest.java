package pro.meisen.boot.web.req;

/**
 * @author meisen
 * 2019-07-13
 */
public class BlogSearchRequest extends PageRequest {

    private String id;

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
    private Integer publish;

    public Integer getPublish() {
        return publish;
    }

    public void setPublish(Integer publish) {
        this.publish = publish;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
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
}

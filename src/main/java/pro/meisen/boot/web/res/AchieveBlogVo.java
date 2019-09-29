package pro.meisen.boot.web.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.Map;

/**
 * 归档文章返回
 * @author meisen
 * 2019-07-15
 */
@ApiModel
public class AchieveBlogVo {
    @ApiModelProperty("文章时间")
    private Date blogDate;
    @ApiModelProperty("文章id")
    private String articleId;
    @ApiModelProperty("文章标题")
    private String title;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public Date getBlogDate() {
        return blogDate;
    }

    public void setBlogDate(Date blogDate) {
        this.blogDate = blogDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

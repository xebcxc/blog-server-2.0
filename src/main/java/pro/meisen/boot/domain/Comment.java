package pro.meisen.boot.domain;

import pro.meisen.boot.domain.common.CommonDomain;

/**
 * 评论
 * @author meisen
 * 2019-07-17
 */
public class Comment extends CommonDomain {

    // 父id, 用户
    private Long parentId;
    // 用户id
    private String userId;
    // 文章id
    private String articleId;
    // 评论顺序
    private Integer order;
    // 是否删除
    private Boolean isDelete;
    // 评论用户名
    private String commentUser;
    // 评论邮箱
    private String commentEmail;
    // 评论者github
    private String commentGithub;
    // 评论内容
    private String content;

    //是否展示
    private Boolean isShow;

    public String getCommentUser() {
        return commentUser;
    }

    public void setCommentUser(String commentUser) {
        this.commentUser = commentUser;
    }

    public String getCommentEmail() {
        return commentEmail;
    }

    public void setCommentEmail(String commentEmail) {
        this.commentEmail = commentEmail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Boolean getShow() {
        return isShow;
    }

    public void setShow(Boolean show) {
        isShow = show;
    }

    public String getCommentGithub() {
        return commentGithub;
    }

    public void setCommentGithub(String commentGithub) {
        this.commentGithub = commentGithub;
    }
}

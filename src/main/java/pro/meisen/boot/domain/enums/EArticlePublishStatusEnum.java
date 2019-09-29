package pro.meisen.boot.domain.enums;

/**
 * 博客发布状态枚举
 * @author meisen
 * @date 2019-09-29
 */
public enum EArticlePublishStatusEnum {
    DRAFT(false, "草稿"),
    PUBLISH(true, "已发布"),
    ;

    private boolean code;

    private String desc;


    EArticlePublishStatusEnum(boolean code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isCode() {
        return code;
    }

    public void setCode(boolean code) {
        this.code = code;
    }
}

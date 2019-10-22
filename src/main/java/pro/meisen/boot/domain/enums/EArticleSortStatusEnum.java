package pro.meisen.boot.domain.enums;

/**
 * @author meisen
 * @date 2019-10-21
 */
public enum EArticleSortStatusEnum {
    NORMAL((byte) 0, "不置顶"),
    TOP((byte) 1, "置顶"),
    ;

    private byte code;

    private String desc;

    EArticleSortStatusEnum(byte code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

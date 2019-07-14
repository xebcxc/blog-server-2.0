package pro.meisen.boot.core.response;

/**
 * 统一响应结果类
 * @author meisen
 * 2019-05-23
 */
public class Result<T> {
    private Integer code;

    private T data;

    private String msg;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

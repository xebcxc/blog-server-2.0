package pro.meisen.boot.core.exception;

/**
 * 应用错误处理类
 * @author meisen
 * 2019-05-23
 */
public class AppException extends RuntimeException {

    private static final long serialVersionUID = -7455556413670313345L;

    private String errorCode;

    private String errorMsg;

    private AppException() {

    }
    public AppException(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}

package pro.meisen.boot.domain.common;

/**
 * @author meisen
 * 2019-07-13
 */
public class ErrorCode {

    // 系统处理错误
    public static final String APP_ERROR_SYSTEM_FAIL = "50000";

    // 参数错误
    public static final String APP_ERROR_PARAM_ILLEGAL = "40000";
    // 文件上传失败
    public static final String APP_FILE_UPLOAD_FAIL = "40001";

    // 权限错误
    public static final String APP_ERROR_AUTH_ILLEGAL = "60001";

    // 登陆用户名
    public static final String APP_ERROR_USERNAME_ILLEGAL = "70001";
    // 登陆密码
    public static final String APP_ERROR_PASSWORD_ILLEGAL = "70002";
    //  用户被冻结
    public static final String APP_ERROR_ACCOUNT_FREEZED = "70003";
    // 账户不存在
    public static final String APP_ERROR_ACCOUNT_NOT_EXISTED = "70004";
    // 未知错误
    public static final String APP_ERROR_UNKOWN_ERROR = "70005";
    // 用户已存在
    public static final String APP_ERROR_ACCOUNT_EXIST = "70006";
    // 无权限
    public static final String APP_ERROR_ACCOUNT_UNAUTH = "70008";

}

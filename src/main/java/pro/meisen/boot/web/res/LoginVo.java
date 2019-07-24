package pro.meisen.boot.web.res;

/**
 * @author meisen
 * 2019-07-22
 */
public class LoginVo {
    private String userId;

    private String username;

    private boolean loginSuccess;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isLoginSuccess() {
        return loginSuccess;
    }

    public void setLoginSuccess(boolean loginSuccess) {
        this.loginSuccess = loginSuccess;
    }
}

package pro.meisen.boot.domain;

import pro.meisen.boot.domain.common.CommonDomain;

/**
 * @author meisen
 * 2019-07-18
 */
public class User extends CommonDomain {
    // 账户名
    private String account;
    // 用户名
    private String username;
    // 密码
    private String password;
    // 角色id
    private String roleId;
    // 是否启用
    private Boolean isActive;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}

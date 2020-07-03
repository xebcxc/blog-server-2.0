package fun.baozi.boot.domain;

import fun.baozi.boot.domain.common.CommonDomain;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author baozi
 * 2019-07-18
 */
@Table(name = "user")
public class User extends CommonDomain {
    // 账户名
    private String account;
    // 用户名
    private String username;
    // 密码
    private String password;
    // 角色id
    @Column(name = "role_id")
    private Integer roleId;
    // 是否启用
    @Column(name = "is_active")
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

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
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

    public String getCredentialsSalt() {
        return username;
    }
}

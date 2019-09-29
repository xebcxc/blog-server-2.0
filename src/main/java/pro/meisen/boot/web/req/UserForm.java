package pro.meisen.boot.web.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author meisen
 * 2019-07-22
 */
@ApiModel
public class UserForm {
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("密码")
    private String password;


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
}

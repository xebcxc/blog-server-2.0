package pro.meisen.boot.web.controller;

import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pro.meisen.boot.core.exception.AppException;
import pro.meisen.boot.dao.service.UserService;
import pro.meisen.boot.domain.User;
import pro.meisen.boot.domain.common.ErrorCode;
import pro.meisen.boot.ext.shiro.encrypt.MD5HashEncryptor;
import pro.meisen.boot.helper.StringHelper;
import pro.meisen.boot.web.req.UserChangeModel;
import pro.meisen.boot.web.req.UserModel;
import pro.meisen.boot.web.res.LoginVo;
import pro.meisen.boot.web.res.RegisterVo;

import javax.servlet.http.HttpServletRequest;

/**
 * @author meisen
 * 2019-07-22
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private StringHelper stringHelper;

    /**
     * 登陆
     * @param request 请求
     * @param userModel 用户模型
     * @return 登陆的数据
     */
    @PostMapping("/login")
    public LoginVo login(HttpServletRequest request, @RequestBody UserModel userModel) {
        // 校验入参
        validateParam(userModel);
        Subject subject = SecurityUtils.getSubject();
        String username = userModel.getUsername().trim();
        String password = userModel.getPassword().trim();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        LoginVo loginVo = new LoginVo();
        try {
            subject.login(token);
            User user = (User) subject.getPrincipal();
            loginVo.setLoginSuccess(true);
            loginVo.setUserId(String.valueOf(user.getId()));
            loginVo.setUsername(user.getUsername());
        } catch (IncorrectCredentialsException e) {
            loginVo.setLoginSuccess(false);
            throw new AppException(ErrorCode.APP_ERROR_PASSWORD_ILLEGAL, "密码错误, 请重新输入.");
        } catch (LockedAccountException e) {
            loginVo.setLoginSuccess(false);
            throw new AppException(ErrorCode.APP_ERROR_ACCOUNT_FREEZED, "用户已被冻结.");
        } catch (UnknownAccountException e) {
            loginVo.setLoginSuccess(false);
            throw new AppException(ErrorCode.APP_ERROR_ACCOUNT_NOT_EXISTED, "不存在该用户.");
        } catch (Exception e) {
            loginVo.setLoginSuccess(false);
            LOGGER.error("登陆失败.username={}", userModel.getUsername());
            throw new AppException(ErrorCode.APP_ERROR_UNKOWN_ERROR, "登陆失败, 请联系管理员.");
        }
        return loginVo;
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.logout();
        } catch (Exception e) {
            throw new AppException(ErrorCode.APP_ERROR_AUTH_ILLEGAL, "登出失败");
        }
    }

    @PostMapping("/register")
    public RegisterVo register(HttpServletRequest request, @RequestBody UserModel userModel) {
        validateParam(userModel);
        String username = userModel.getUsername();
        User user = userService.findByUsername(username);
        if (null != user) {
            throw new AppException(ErrorCode.APP_ERROR_ACCOUNT_EXIST, "用户名已存在, 请重新输入.");
        }
        // 注册用户
        user = registerUser(userModel);
        return assembleRegisterVo(user);
    }

    @PutMapping("/password/change")
    public Boolean changePassword(HttpServletRequest request, @RequestBody UserChangeModel userModel) {
        if (Strings.isEmpty(userModel.getUsername())) {
            throw new AppException(ErrorCode.APP_ERROR_USERNAME_ILLEGAL, "请输入用户名");
        }
        if (Strings.isEmpty(userModel.getOldPassword())) {
            throw new AppException(ErrorCode.APP_ERROR_PASSWORD_ILLEGAL, "请输入旧密码");
        }
        if (Strings.isEmpty(userModel.getNewPassword())) {
            throw new AppException(ErrorCode.APP_ERROR_PASSWORD_ILLEGAL, "请输入新密码");
        }
        String username = userModel.getUsername().trim();
        User user = userService.findByUsername(username);
        if (null == user) {
            throw new AppException(ErrorCode.APP_ERROR_ACCOUNT_NOT_EXISTED, "用户存在, 请确认用户名是否正确.");
        }
        String oldPassword = userModel.getOldPassword().trim();
        String newPassword = userModel.getNewPassword().trim();
        oldPassword = new MD5HashEncryptor.Builder(oldPassword).salt(user.getUsername()).build().toHex();
        if (!user.getPassword().equals(oldPassword)) {
            throw new AppException(ErrorCode.APP_ERROR_PASSWORD_ILLEGAL, "旧密码错误, 无法更新新密码.");
        }
        newPassword = new MD5HashEncryptor.Builder(newPassword).salt(user.getUsername()).build().toHex();
        user.setPassword(newPassword);
        userService.update(user);
        return true;
    }

    /**
     * 更新用户状态
     * @param request 请求
     * @param userModel 用户模型
     */
    @PutMapping("/status/change")
    public void updateStatus(HttpServletRequest request, @RequestBody UserChangeModel userModel) {
        if (Strings.isEmpty(userModel.getUserId()) || !stringHelper.isNumeric(userModel.getUserId()) || null == userModel.isActive()) {
            throw new AppException(ErrorCode.APP_ERROR_PARAM_ILLEGAL, "参数错误,  请刷新后重试.");
        }
        Long id = Long.valueOf(userModel.getUserId());
        userService.updateUserStatus(id, userModel.isActive());
    }

    @RequestMapping(path = "/unauth")
    public void unauth() {
        throw new AppException(ErrorCode.APP_ERROR_ACCOUNT_UNAUTH, "无权限,请重新登录.");
    }


    /**
     * 组装registerVo
     * @param user 注册后的用户
     * @return RegisterVo
     */
    private RegisterVo assembleRegisterVo(User user) {
        RegisterVo vo = new RegisterVo();
        vo.setUserId(String.valueOf(user.getId()));
        vo.setUsername(user.getUsername());
        vo.setAccount(user.getAccount());
        return vo;
    }

    /**
     * 注册
     * @param userModel 需要注册的信息
     * @return 注册后的用户
     */
    private User registerUser(UserModel userModel) {
        User user = new User();
        String username = userModel.getUsername().trim();
        user.setUsername(username);
        String password = new MD5HashEncryptor.Builder(userModel.getPassword()).salt(username).build().toHex();
        user.setPassword(password);
        user.setActive(true);
        user.setAccount("");
        userService.save(user);
        return user;
    }

    /**
     * 校验用户请求
     * @param userModel  用户请求
     */
    private void validateParam(UserModel userModel) {
        if (Strings.isEmpty(userModel.getUsername())) {
            throw new AppException(ErrorCode.APP_ERROR_USERNAME_ILLEGAL, "请输入用户名");
        }
        if (userModel.getUsername().length() > 10) {
            throw new AppException(ErrorCode.APP_ERROR_USERNAME_ILLEGAL, "用户名过长");
        }
        if (Strings.isEmpty(userModel.getPassword())) {
            throw new AppException(ErrorCode.APP_ERROR_PASSWORD_ILLEGAL, "请输入密码");
        }
        if (userModel.getPassword().length() < 6) {
            throw new AppException(ErrorCode.APP_ERROR_PASSWORD_ILLEGAL, "密码太短,至少输入6位.");
        }

    }

}

package fun.baozi.boot.web.controller;

import fun.baozi.boot.core.exception.AppException;
import fun.baozi.boot.dao.service.UserService;
import fun.baozi.boot.domain.User;
import fun.baozi.boot.domain.common.ErrorCode;
import fun.baozi.boot.ext.shiro.encrypt.MD5HashEncryptor;
import fun.baozi.boot.helper.StringHelper;
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
import fun.baozi.boot.web.req.UserChangeForm;
import fun.baozi.boot.web.req.UserForm;
import fun.baozi.boot.web.res.LoginVo;
import fun.baozi.boot.web.res.RegisterVo;

import javax.servlet.http.HttpServletRequest;

/**
 * @author baozi
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
     * @param userForm 用户模型
     * @return 登陆的数据
     */
    @PostMapping("/login")
    public LoginVo login(HttpServletRequest request, @RequestBody UserForm userForm) {
        // 校验入参
        validateParam(userForm);
        Subject subject = SecurityUtils.getSubject();
        String username = userForm.getUsername().trim();
        String password = userForm.getPassword().trim();
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
            LOGGER.error("登陆失败.username={}", userForm.getUsername());
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

    @GetMapping("/currentUser")
    public User currentUser(HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        Object principal = subject.getPrincipal();
        if (null == principal && !(principal instanceof User)) {
            throw new AppException(ErrorCode.APP_ERROR_AUTH_ILLEGAL, "权限错误,请重试");
        }
        User user = (User) principal;
        user.setPassword(null);
        return user;
    }

    @PostMapping("/register")
    public RegisterVo register(HttpServletRequest request, @RequestBody UserForm userForm) {
        validateParam(userForm);
        String username = userForm.getUsername();
        User user = userService.findByUsername(username);
        if (null != user) {
            throw new AppException(ErrorCode.APP_ERROR_ACCOUNT_EXIST, "用户名已存在, 请确认输入.");
        }
        // 注册用户
        user = registerUser(userForm);
        return assembleRegisterVo(user);
    }

    @PutMapping("/password/change")
    public Boolean changePassword(HttpServletRequest request, @RequestBody UserChangeForm userModel) {
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
        userService.updateByPrimaryKeySelective(user);
        return true;
    }

    /**
     * 更新用户状态
     * @param request 请求
     * @param userModel 用户模型
     */
    @PutMapping("/status/change")
    public void updateStatus(HttpServletRequest request, @RequestBody UserChangeForm userModel) {
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
     * @param userForm 需要注册的信息
     * @return 注册后的用户
     */
    private User registerUser(UserForm userForm) {
        User user = new User();
        String username = userForm.getUsername().trim();
        user.setUsername(username);
        String password = new MD5HashEncryptor.Builder(userForm.getPassword()).salt(username).build().toHex();
        user.setPassword(password);
        user.setActive(true);
        user.setAccount("");
        userService.insertSelective(user);
        return user;
    }

    /**
     * 校验用户请求
     * @param userForm  用户请求
     */
    private void validateParam(UserForm userForm) {
        if (Strings.isEmpty(userForm.getUsername())) {
            throw new AppException(ErrorCode.APP_ERROR_USERNAME_ILLEGAL, "请输入用户名");
        }
        if (userForm.getUsername().length() > 10) {
            throw new AppException(ErrorCode.APP_ERROR_USERNAME_ILLEGAL, "用户名过长");
        }
        if (Strings.isEmpty(userForm.getPassword())) {
            throw new AppException(ErrorCode.APP_ERROR_PASSWORD_ILLEGAL, "请输入密码");
        }
        if (userForm.getPassword().length() < 6) {
            throw new AppException(ErrorCode.APP_ERROR_PASSWORD_ILLEGAL, "密码太短,至少输入6位.");
        }

    }

}

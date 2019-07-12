package com.coolcloud.sacw.system.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coolcloud.sacw.common.Result;
import com.coolcloud.sacw.common.util.Assert;
import com.coolcloud.sacw.system.entity.ChangePasswordForm;
import com.coolcloud.sacw.system.entity.Permission;
import com.coolcloud.sacw.system.entity.User;
import com.coolcloud.sacw.system.service.SystemService;

/**
 * 系统控制类
 * 
 * @author xyz
 *
 * @date 2018年4月18日 上午10:53:31
 */
@RestController
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private SystemService systemService;

    /**
     * 当前登录用户信息
     * 
     * @return
     */
    @GetMapping(value = "/current-user")
    public Result currentUser() {
        User user = systemService.currentUser();
        return Result.success().add("user", user);
    }

    /**
     * 修改当前登录用户密码
     * 
     * @param oldPass
     *            原始密码
     * @param newPass
     *            新密码
     * @param rePass
     *            确认新密码
     * @return
     */
    @PostMapping(value = "/change-password")
    public Result changePassword(@Validated ChangePasswordForm form) {

        Assert.isTrue(form.getRePassword().equals(form.getNewPassword()), "两次输入密码不一致");

        systemService.changePassword(form.getOldPassword(), form.getNewPassword());
        return Result.success("密码修改成功，请用新密码重新登录");
    }

    /**
     * 当前登录用户权限
     * 
     * @return
     */
    @GetMapping(value = "/permissions")
    public Object permissions() {
        List<Permission> permissions = systemService.permissions();
        return permissions;
    }

    /**
     * 验证当前用户密码是否正确
     * 
     * @return
     */
    @PostMapping("/validate-password")
    public Result validatePassword(@RequestParam("password") String password) {
        boolean isCorrect = systemService.validatePassword(password);
        return Result.success().add("valid", isCorrect);
    }

}

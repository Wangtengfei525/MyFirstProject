package com.coolcloud.sacw.system.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coolcloud.sacw.common.BaseService;
import com.coolcloud.sacw.common.util.Assert;
import com.coolcloud.sacw.common.util.SystemUtil;
import com.coolcloud.sacw.system.entity.Permission;
import com.coolcloud.sacw.system.entity.User;

@Service
public class SystemService extends BaseService<User, String> {

    private static final String ADMIN_USERNAME = "admin";

    private static final String ADMIN_PASSWORD = "123456";

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 当前登录用户信息
     * 
     * @return
     * @throws Exception
     */
    public User currentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user;
        if ("admin".equals(username)) {
            user = new User();
            user.setName(username);
        } else {
            user = userService.getByName(username);
        }

        return user;
    }

    /**
     * 当前登录用户权限
     * 
     * @return
     * @throws Exception
     */
    public List<Permission> permissions() {

        User user = currentUser();
        List<Permission> permissions;

        if (ADMIN_USERNAME.equals(user.getName())) {
            permissions = permissionService.getAll();
        } else {
            permissions = permissionService.getByUser(user.getId());
        }

        List<Permission> list = new ArrayList<>();

        for (Permission permission : permissions) {
            if (permission.getParentId() == null) {
                list.add(permission);
            }
            for (Permission p : permissions) {
                if (p.getParentId() != null && p.getParentId().equals(permission.getId())) {
                    permission.addChild(p);
                }
            }
        }
        return list;
    }

    /**
     * 重设密码
     * 
     * @param oldPassword
     *            旧密码
     * @param newPassword
     *            新密码
     */
    @Transactional
    public void changePassword(String oldPassword, String newPassword) {

        String username = SystemUtil.currentUser();
        User user = userService.getByName(username);

        Assert.isTrue(!ADMIN_USERNAME.equals(username), "暂不支持更改管理员密码");

        Assert.notNull(user, "用户不存在");
        String originPassword = user.getPassword();

        Assert.isTrue(passwordEncoder.matches(oldPassword, originPassword), "原密码错误");

        user.setPassword(passwordEncoder.encode(newPassword));

        userService.update(user);
    }

    /**
     * 验证当前登录用户的密码
     * 
     * @param password
     * @return
     */
    public boolean validatePassword(String password) {

        String username = SystemUtil.currentUser();
        if (SystemUtil.isAdmin()) {
            return ADMIN_PASSWORD.equals(password);
        } else {
            User user = userService.getByName(username);
            Assert.notNull(user, "用户不存在");
            return passwordEncoder.matches(password, user.getPassword());
        }

    }
}

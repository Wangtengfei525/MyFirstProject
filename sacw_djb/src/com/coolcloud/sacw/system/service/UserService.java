package com.coolcloud.sacw.system.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.coolcloud.sacw.common.BaseService;
import com.coolcloud.sacw.common.util.Assert;
import com.coolcloud.sacw.system.entity.User;
import com.coolcloud.sacw.system.entity.UserExample;
import com.coolcloud.sacw.system.entity.UserRole;
import com.coolcloud.sacw.system.mapper.UserMapper;
import com.coolcloud.sacw.system.mapper.UserRoleMapper;

@Service
public class UserService extends BaseService<User, String> {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 覆写删除方法，先删除与角色的关联信息再删除自身
     */
    @Override
    @Transactional
    public int delete(String id) {
        // 删除与角色的关联信息
        userRoleMapper.deleteByUserId(id);
        // 再删除自身
        return super.delete(id);
    }

    @Override
    @Transactional
    public int deleteBatch(List<String> ids) {
        // 删除与角色的关联信息
        if (ids != null && ids.size() > 0) {
            userRoleMapper.deleteByUserIdBatch(ids);
        }
        // 再删除自身
        return super.deleteBatch(ids);

    }

    /**
     * 设置角色
     * 
     * @param id
     * @param list
     * @return
     */
    @Transactional
    public int assignRoles(String userId, List<String> roles) {
        // 先删除已有角色
        userRoleMapper.deleteByUserId(userId);
        // 再添加角色
        List<UserRole> userRoles = new ArrayList<>();
        Set<String> set = new HashSet<>(roles);
        for (String roleId : set) {
            if (roleId != null) {
                userRoles.add(new UserRole(userId, roleId));
            }
        }
        Integer num = 0;
        if (userRoles.size() > 0) {
            num = userRoleMapper.insertBatch(userRoles);
        }
        return num;
    }

    /**
     * 新增用户时，判断该用户是否已经存在
     * 
     * @return
     */
    public boolean userExcite(User user) {
        boolean bo = false;
        UserExample userExample = new UserExample();
        userExample.setName(user.getName());
        try {
            List<User> users = this.getByExample(userExample);
            if (users.size() != 0) {
                bo = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            bo = true;
            return bo;
        }
        return bo;
    }

    /**
     * 分配角色
     * 
     * @param id
     * @param roles
     * @return
     */
    public Integer assignPermissions(String id, String roles) {
        Assert.isTrue(StringUtils.hasText(id), "用户id不正确");
        Assert.isTrue(this.exists(id), "用户不存在");
        List<String> list = new ArrayList<>();
        if (StringUtils.hasText(roles)) {
            for (String string : roles.split(",")) {
                list.add(string);
            }
        }
        Integer num = this.assignRoles(id, list);
        return num;
    }

    /**
     * 根据用户名获取用户
     * 
     * @param username
     *            用户名
     * @return 用户
     */
    @Transactional(readOnly = true)
    public User getByName(String username) {
        return userMapper.selectByName(username);
    }

}

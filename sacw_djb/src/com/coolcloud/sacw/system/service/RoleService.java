package com.coolcloud.sacw.system.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coolcloud.sacw.common.BaseService;
import com.coolcloud.sacw.system.entity.Role;
import com.coolcloud.sacw.system.entity.RolePermission;
import com.coolcloud.sacw.system.mapper.RoleMapper;
import com.coolcloud.sacw.system.mapper.RolePermissionMapper;
import com.coolcloud.sacw.system.mapper.UserRoleMapper;

@Service
public class RoleService extends BaseService<Role, String> {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 分配权限
     * 
     * @param role
     * @return
     */
    @Transactional
    public Integer assignPermissions(String roleId, List<String> permissionIds) {
        // 先删除已有权限
        rolePermissionMapper.deleteByRoleId(roleId);
        // 再添加权限
        Set<String> set = new HashSet<>(permissionIds);
        List<RolePermission> rolePermissions = new ArrayList<>();
        for (String permissionId : set) {
            if (permissionId != null) {
                rolePermissions.add(new RolePermission(roleId, permissionId));
            }
        }
        Integer num = 0;
        if (rolePermissions.size() > 0) {
            num += rolePermissionMapper.insertBatch(rolePermissions);
        }
        return num;
    }

    /**
     * 覆写默认删除方法，先删除与权限的关联信息再删除自身
     */
    @Override
    @Transactional
    public int delete(String id) {
        if (id != null) {
            // 删除与权限的关联信息
            rolePermissionMapper.deleteByRoleId(id);
            // 删除与用户的关联信息
            userRoleMapper.deleteByRoleId(id);
        }
        // 删除自身信息
        return super.delete(id);
    }

    /**
     * 覆写默认批量删除方法，先删除与权限的关联信息再删除自身
     */
    @Override
    @Transactional
    public int deleteBatch(List<String> ids) {
        if (ids.size() > 0) {
            // 删除与权限的关联信息
            rolePermissionMapper.deleteByRoleIdBatch(ids);
            // 删除与用户的关联信息
            userRoleMapper.deleteByRoleIdBatch(ids);
        }
        // 删除自身信息
        return super.deleteBatch(ids);
    }

    /**
     * 获取用户的角色状态
     * 
     * @param userId
     *            用户id
     * @return
     */
    @Transactional(readOnly = true)
    public List<Role> getByUser(String userId) {
        return roleMapper.selectByUserId(userId);
    }

}
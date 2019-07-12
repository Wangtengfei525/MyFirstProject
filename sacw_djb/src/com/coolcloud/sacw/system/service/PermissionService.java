package com.coolcloud.sacw.system.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coolcloud.sacw.common.BaseService;
import com.coolcloud.sacw.system.entity.Permission;
import com.coolcloud.sacw.system.mapper.PermissionMapper;
import com.coolcloud.sacw.system.mapper.RolePermissionMapper;

@Service
public class PermissionService extends BaseService<Permission, String> {

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    /**
     * 覆写默认的删除方法，先删除与角色的关联信息，再删除自身
     */
    @Override
    @Transactional
    public int delete(String id) {
        Permission permission = this.tree(id);
        Integer num = 0;
        if (permission != null) {
            // 先删除子权限
            num += deleteChildren(permission);
            // 删除与角色的关联信息
            rolePermissionMapper.deleteByPermissionId(id);
            // 再删除本身， 调用BaseService中的删除方法
            num += super.delete(id);
        }
        return num;
    }

    /**
     * 覆写默认的批量删除方法
     */
    @Override
    @Transactional
    public int deleteBatch(List<String> ids) {
        Integer num = 0;
        for (String id : ids) {
            Permission permission = tree(id);
            num += deleteChildren(permission);
        }
        // 删除与角色的关联信息
        if (ids.size() > 0) {
            rolePermissionMapper.deleteByPermissionIdBatch(ids);
        }
        // 调用BaseService中的批量删除方法
        num += super.deleteBatch(ids);
        return num;
    }

    /**
     * 递归删除子权限
     * 
     * @param permissions
     * @return
     */
    @Transactional
    private int deleteChildren(Permission permission) {
        List<String> ids = new ArrayList<>();
        Integer num = 0;
        for (Permission child : permission.getChildren()) {
            ids.add(child.getId());
            num += deleteChildren(child);
        }
        // 删除与角色的关联信息
        if (ids.size() > 0) {
            rolePermissionMapper.deleteByPermissionIdBatch(ids);
        }
        // 调用BaseService中的批量删除方法
        num += super.deleteBatch(ids);
        return num;
    }

    /**
     * 获取树状权限列表
     * 
     * @param tops
     *            根节点id，若为 null 或 size 为 0，以 parentId 为空的作为根节点
     * @return
     */
    public List<Permission> tree(List<String> tops) {
        List<Permission> permissions = this.getAll();
        List<Permission> tree = new ArrayList<>();
        if (tops == null || tops.size() == 0) {
            for (Permission permission : permissions) {
                if (permission.getParentId() == null) {
                    tree.add(permission);
                }
                for (Permission p : permissions) {
                    if (p.getParentId() != null && p.getParentId().equals(permission.getId())) {
                        permission.addChild(p);
                    }
                }
            }
        } else {
            for (Permission permission : permissions) {
                if (tops.contains(permission.getId())) {
                    tree.add(permission);
                    for (Permission p : permissions) {
                        if (p.getParentId() != null && p.getParentId().equals(permission.getId())) {
                            permission.addChild(p);
                        }
                    }
                }
            }
        }

        return tree;
    }

    /**
     * 获取指定权限的树状权限列表
     * 
     * @return 权限id
     */
    private Permission tree(String id) {
        List<String> tops = new ArrayList<>();
        tops.add(id);
        List<Permission> permissions = tree(tops);
        if (permissions.size() == 1) {
            return permissions.get(0);
        }
        return null;
    }

    /**
     * 根据角色id查找权限，该方法返回系统中所有的权限，但在checked属性中标注了是否拥有该属性
     * 
     * @param id
     *            角色id
     * @return
     */
    @Transactional(readOnly = true)
    public List<Permission> getByRole(String id) {
        if (id == null) {
            return new ArrayList<>();
        }
        return permissionMapper.selectByRoleId(id);
    }

    /**
     * 查询用户具有的权限
     * 
     * @param id
     *            用户id
     * @return
     */
    @Transactional(readOnly = true)
    public List<Permission> getByUser(String id) {
        if (id == null) {
            return new ArrayList<>();
        }
        return permissionMapper.selectByUserId(id);
    }

}

package com.coolcloud.sacw.system.mapper;

import java.util.List;

import com.coolcloud.sacw.common.BaseMapper;
import com.coolcloud.sacw.system.entity.RolePermission;

/**
 * 角色-权限Mapper
 * 
 * @author xyz
 *
 */
public interface RolePermissionMapper extends BaseMapper<RolePermission, String> {

    public int deleteByRoleIdBatch(List<String> roleIds);

    public int deleteByPermissionIdBatch(List<String> permissionIds);

    /**
     * 按权限id删除
     * 
     * @param id
     */
    public int deleteByPermissionId(String id);

    /**
     * 按角色id删除
     * 
     * @param id
     */
    public int deleteByRoleId(String id);

}

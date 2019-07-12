package com.coolcloud.sacw.system.mapper;

import java.util.List;

import com.coolcloud.sacw.common.BaseMapper;
import com.coolcloud.sacw.system.entity.UserRole;

public interface UserRoleMapper extends BaseMapper<UserRole, String> {

    public Integer deleteByUserIdBatch(List<String> userIds);

    public Integer deleteByRoleIdBatch(List<String> roleIds);

    /**
     * 按角色id删除
     * 
     * @param id
     */
    public void deleteByRoleId(String roleId);

    /**
     * 按用户id删除
     * 
     * @param id
     */
    public void deleteByUserId(String userId);

}

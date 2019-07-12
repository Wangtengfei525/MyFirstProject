package com.coolcloud.sacw.system.mapper;

import java.util.List;

import com.coolcloud.sacw.common.BaseMapper;
import com.coolcloud.sacw.system.entity.Permission;

public interface PermissionMapper extends BaseMapper<Permission, String> {

	public List<Permission> selectByRoleId(String roleId);

	public List<Permission> selectByUserId(String userId);

}
package com.coolcloud.sacw.system.mapper;

import java.util.List;

import com.coolcloud.sacw.common.BaseMapper;
import com.coolcloud.sacw.system.entity.Role;

public interface RoleMapper extends BaseMapper<Role, String> {

	public List<Role> selectByUserId(String userId);

}

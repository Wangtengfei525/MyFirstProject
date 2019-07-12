package com.coolcloud.sacw.system.entity;

import com.coolcloud.sacw.common.BaseEntity;

/**
 * 用户-角色关联表
 * 
 * @author xyz
 *
 */
public class UserRole extends BaseEntity {

	private static final long serialVersionUID = -3765374529720360414L;

	private String userId;

	private String roleId;

	public UserRole() {
	}

	public UserRole(String userId, String roleId) {
		this.userId = userId;
		this.roleId = roleId;
	}

	public String getUserId() {
		return userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}

package com.coolcloud.sacw.system.entity;

import com.coolcloud.sacw.common.BaseExample;

public class UserRoleExample extends BaseExample {
	private String userId;
	private String roleId;

	public UserRoleExample() {
	}

	public UserRoleExample(String userId, String roleId) {
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

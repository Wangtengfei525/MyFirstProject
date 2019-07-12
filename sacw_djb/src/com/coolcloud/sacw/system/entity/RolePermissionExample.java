package com.coolcloud.sacw.system.entity;

import com.coolcloud.sacw.common.BaseExample;

public class RolePermissionExample extends BaseExample {
	private String roleId;
	private String permissionId;

	public RolePermissionExample() {
	}

	public RolePermissionExample(String roleId, String permissionId) {
		this.roleId = roleId;
		this.permissionId = permissionId;
	}

	public String getRoleId() {
		return roleId;
	}

	public String getPermissionId() {
		return permissionId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}

}

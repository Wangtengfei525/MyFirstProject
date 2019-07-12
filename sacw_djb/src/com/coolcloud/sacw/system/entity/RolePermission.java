package com.coolcloud.sacw.system.entity;

import com.coolcloud.sacw.common.BaseEntity;

/**
 * 角色-权限关联表
 * 
 * @author xyz
 *
 */
public class RolePermission extends BaseEntity {

	private static final long serialVersionUID = -3126496570823638864L;

	private String roleId;

	private String permissionId;

	public RolePermission() {
	}

	public RolePermission(String roleId, String permissionId) {
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

package com.coolcloud.sacw.system.entity;

import com.coolcloud.sacw.common.BaseExample;

public class PermissionExample extends BaseExample {

	private String name;
	private String nameLike;
	private String url;
	private String urlLike;
	private String parentId;
	private Integer display;
	// 接收roleId参数
	private String roleId;
	// 接收tree参数
	private Boolean tree;

	public String getName() {
		return name;
	}

	public String getNameLike() {
		return nameLike;
	}

	public String getUrl() {
		return url;
	}

	public String getUrlLike() {
		return urlLike;
	}

	public String getParentId() {
		return parentId;
	}

	public Integer getDisplay() {
		return display;
	}

	public String getRoleId() {
		return roleId;
	}

	public Boolean getTree() {
		return tree;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUrlLike(String urlLike) {
		this.urlLike = urlLike;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public void setDisplay(Integer display) {
		this.display = display;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public void setTree(Boolean tree) {
		this.tree = tree;
	}

}

package com.coolcloud.sacw.system.entity;

import com.coolcloud.sacw.common.BaseExample;

public class RoleExample extends BaseExample {
	private String name;
	private String nameLike;
	private String userId;

	public String getName() {
		return name;
	}

	public String getNameLike() {
		return nameLike;
	}

	public String getUserId() {
		return userId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}

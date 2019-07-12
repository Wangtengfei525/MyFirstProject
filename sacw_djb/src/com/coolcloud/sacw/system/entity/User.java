package com.coolcloud.sacw.system.entity;

import java.util.Date;

import javax.validation.constraints.Pattern;

import com.coolcloud.sacw.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class User extends BaseEntity {

	private static final long serialVersionUID = -2235740864473501435L;

	/* @Pattern(regexp = "\\w{3,24}", message = "无效的用户名") */
	private String name;

	private String nickname;

	@JsonIgnore
	@Pattern(regexp = "\\w{6,32}", message = "无效的密码")
	private String password;

	private String email;

	private String mobile;

	private Integer state;

	private String unit;

	private String unitName;

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * 最后登录时间
	 */
	private Date lastLoginTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

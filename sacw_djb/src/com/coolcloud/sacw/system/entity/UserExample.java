package com.coolcloud.sacw.system.entity;

import com.coolcloud.sacw.common.BaseExample;

public class UserExample extends BaseExample {
	private String name;
	private String nameLike;
	private String nickname;
	private String nicknameLike;
	private String email;
	private String emailLike;
	private String password;
	private String passwordLike;
	private String mobile;
	private String mobileLike;
	private Integer state;
    private String unit;
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getName() {
		return name;
	}

	public String getNameLike() {
		return nameLike;
	}

	public String getNickname() {
		return nickname;
	}

	public String getNicknameLike() {
		return nicknameLike;
	}

	public String getEmail() {
		return email;
	}

	public String getEmailLike() {
		return emailLike;
	}

	public String getPassword() {
		return password;
	}

	public String getPasswordLike() {
		return passwordLike;
	}

	public String getMobile() {
		return mobile;
	}

	public String getMobileLike() {
		return mobileLike;
	}

	public Integer getState() {
		return state;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setNicknameLike(String nicknameLike) {
		this.nicknameLike = nicknameLike;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEmailLike(String emailLike) {
		this.emailLike = emailLike;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPasswordLike(String passwordLike) {
		this.passwordLike = passwordLike;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setMobileLike(String mobileLike) {
		this.mobileLike = mobileLike;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}

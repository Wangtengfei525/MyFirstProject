package com.coolcloud.sacw.system.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.coolcloud.sacw.common.BaseEntity;

public class Role extends BaseEntity {

	private static final long serialVersionUID = -2410903709517631376L;

	@NotBlank(message = "角色名不能为空")
	private String name;

	@Length(max = 255, message = "角色描述超长，请限制在255字符以内")
	private String description;

	private Boolean checked;

	private List<Permission> permissions = new ArrayList<>();

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Boolean getChecked() {
		return checked;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public void addPermission(Permission permission) {
		if (permission != null) {
			permissions.add(permission);
		}
	}
}

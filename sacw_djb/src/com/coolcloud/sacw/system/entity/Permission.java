package com.coolcloud.sacw.system.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.coolcloud.sacw.common.BaseEntity;

/**
 * 系统权限
 * 
 * @author xyz
 *
 */
public class Permission extends BaseEntity {

	private static final long serialVersionUID = -3259892715838505308L;

	@NotBlank(message = "权限名称不能为空")
	private String name;

	private String url;

	@Length(max = 255, message = "权限描述超长，请限制在255个字符以内")
	private String description;

	private Integer display;

	private String parentId;

	private Boolean checked;

	private Integer sort;

	private List<Permission> children = new ArrayList<>();

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public String getDescription() {
		return description;
	}

	public Integer getDisplay() {
		return display;
	}

	public String getParentId() {
		return parentId;
	}

	public Boolean getChecked() {
		return checked;
	}

	public List<Permission> getChildren() {
		return children;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDisplay(Integer display) {
		this.display = display;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public void setChildren(List<Permission> children) {
		this.children = children;
	}

	public void addChild(Permission permission) {
		this.children.add(permission);
	}

}

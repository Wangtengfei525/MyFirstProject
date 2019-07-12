package com.coolcloud.sacw.system.entity;

import java.util.ArrayList;
import java.util.List;

import com.coolcloud.sacw.common.PreSortEntity;

/**
 * 交换单位
 * 
 * @author 袁永祥
 *
 * @date 2017年12月21日 下午4:32:27
 */
public class Unit extends PreSortEntity {

	private static final long serialVersionUID = 971071657696429642L;

	private String type;

	private String code;

	private String parentId;

	private String name;

	private String remark;

	private List<Unit> children = new ArrayList<>();

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<Unit> getChildren() {
		return children;
	}

	public void setChildren(List<Unit> children) {
		this.children = children;
	}

}
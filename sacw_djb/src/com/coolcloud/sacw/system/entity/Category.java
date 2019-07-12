package com.coolcloud.sacw.system.entity;

import java.util.ArrayList;
import java.util.List;

import com.coolcloud.sacw.common.BaseEntity;

/**
 * 分类代码
 * 
 * @author 袁永祥
 *
 * @date 2017年12月26日 下午3:26:45
 */
public class Category extends BaseEntity {

	private static final long serialVersionUID = -6070907392653718485L;

	private String typeId;

	private String typeCode;

	private String parentId;

	private String parentCode;

	private String name;

	private String code;

	private Integer sort;

	private List<Category> children = new ArrayList<>();

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public List<Category> getChildren() {
		return children;
	}

	public void setChildren(List<Category> children) {
		this.children = children;
	}

}
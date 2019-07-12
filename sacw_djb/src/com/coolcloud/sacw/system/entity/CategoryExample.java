package com.coolcloud.sacw.system.entity;

import com.coolcloud.sacw.common.BaseExample;

public class CategoryExample extends BaseExample {

	private String typeId;

	private String typeCode;

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

}

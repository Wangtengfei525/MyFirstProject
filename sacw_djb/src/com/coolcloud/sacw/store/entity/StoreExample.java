package com.coolcloud.sacw.store.entity;

import java.util.Date;

import com.coolcloud.sacw.common.BaseExample;

public class StoreExample extends BaseExample {

	private String storeName;

	private String parentId;

	private String storeUnitName;

	private Date createTime;

	private String storeContro;

	private String treeType;

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getStoreUnitName() {
		return storeUnitName;
	}

	public void setStoreUnitName(String storeUnitName) {
		this.storeUnitName = storeUnitName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getStoreContro() {
		return storeContro;
	}

	public void setStoreContro(String storeContro) {
		this.storeContro = storeContro;
	}

	public String getTreeType() {
		return treeType;
	}

	public void setTreeType(String treeType) {
		this.treeType = treeType;
	}

}

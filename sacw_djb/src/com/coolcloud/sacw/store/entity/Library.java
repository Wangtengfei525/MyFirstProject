package com.coolcloud.sacw.store.entity;

import com.coolcloud.sacw.common.BaseEntity;

/**
 * 盘库扫描信息
 * @author
 *
 */
public class Library extends BaseEntity{

	private static final long serialVersionUID = -8613979768597989051L;
	/**
	 * 二维码
	 */
	private String evm;

	public String getEvm() {
		return evm;
	}

	public void setEvm(String evm) {
		this.evm = evm;
	}
	
}

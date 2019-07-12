package com.coolcloud.sacw.photo.entity;

import com.coolcloud.sacw.common.BaseExample;
/**
 * 照片查询实体类
 * @author 王腾飞
 *2018年12月28日上午11:01:33
 */
public class PhotoExample extends BaseExample {
	private String propertyId; // 财物id
	private String platCaseCode; // 平台案件编号
	private String property_num; // 财物编号
	private String property_name; // 财物名称

	private String exchangeId;

	public PhotoExample() {
	}

	public PhotoExample(String platCaseCode) {
		this.platCaseCode = platCaseCode;
		this.setDeleted(1);
	}

	public String getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}

	public String getPlatCaseCode() {
		return platCaseCode;
	}

	public void setPlatCaseCode(String platCaseCode) {
		this.platCaseCode = platCaseCode;
	}

	public String getProperty_num() {
		return property_num;
	}

	public void setProperty_num(String property_num) {
		this.property_num = property_num;
	}

	public String getProperty_name() {
		return property_name;
	}

	public void setProperty_name(String property_name) {
		this.property_name = property_name;
	}

	public String getExchangeId() {
		return exchangeId;
	}

	public void setExchangeId(String exchangeId) {
		this.exchangeId = exchangeId;
	}
}

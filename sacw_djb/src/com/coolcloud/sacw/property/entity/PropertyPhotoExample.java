package com.coolcloud.sacw.property.entity;

import com.coolcloud.sacw.common.BaseExample;
/**
 * 财物照片查询实体类
 * @author 
 *
 */
public class PropertyPhotoExample extends BaseExample{
	 private String propertyName; 
	 private String propertyTypeCode; 
	 private String goodsSpecial;
	 private String platCaseCode;
	 private String saveLocationCode;
     private String property_num; //财物编号
     private String property_name; //财物名称
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getPropertyTypeCode() {
		return propertyTypeCode;
	}
	public void setPropertyTypeCode(String propertyTypeCode) {
		this.propertyTypeCode = propertyTypeCode;
	}
	public String getGoodsSpecial() {
		return goodsSpecial;
	}
	public void setGoodsSpecial(String goodsSpecial) {
		this.goodsSpecial = goodsSpecial;
	}
	public String getPlatCaseCode() {
		return platCaseCode;
	}
	public void setPlatCaseCode(String platCaseCode) {
		this.platCaseCode = platCaseCode;
	}
	public String getSaveLocationCode() {
		return saveLocationCode;
	}
	public void setSaveLocationCode(String saveLocationCode) {
		this.saveLocationCode = saveLocationCode;
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
}

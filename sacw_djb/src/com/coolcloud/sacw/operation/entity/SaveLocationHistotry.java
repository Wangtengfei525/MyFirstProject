package com.coolcloud.sacw.operation.entity;

import com.coolcloud.sacw.common.BaseEntity;

public class SaveLocationHistotry extends BaseEntity{
      private String property_name;    //财物名称
      private String kwbm;             //柜物编码
      private Integer number;          //数量
      private Integer remake_number;    //备注数量
      private String case_name;        //案件名称
      private String permit_unit_mc;   //保管单位名称
      private String property_type;    //财物类型
      private String property_status;  //财物状态
      private String case_id;          //案件id
      private String property_id;      //财物id
      private String property_split_id; //财物拆分id
	public String getProperty_name() {
		return property_name;
	}
	public void setProperty_name(String property_name) {
		this.property_name = property_name;
	}
	public String getKwbm() {
		return kwbm;
	}
	public void setKwbm(String kwbm) {
		this.kwbm = kwbm;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Integer getRemake_number() {
		return remake_number;
	}
	public void setRemake_number(Integer remake_number) {
		this.remake_number = remake_number;
	}
	public String getCase_name() {
		return case_name;
	}
	public void setCase_name(String case_name) {
		this.case_name = case_name;
	}
	public String getPermit_unit_mc() {
		return permit_unit_mc;
	}
	public void setPermit_unit_mc(String permit_unit_mc) {
		this.permit_unit_mc = permit_unit_mc;
	}
	public String getProperty_type() {
		return property_type;
	}
	public void setProperty_type(String property_type) {
		this.property_type = property_type;
	}
	public String getProperty_status() {
		return property_status;
	}
	public void setProperty_status(String property_status) {
		this.property_status = property_status;
	}
	public String getCase_id() {
		return case_id;
	}
	public void setCase_id(String case_id) {
		this.case_id = case_id;
	}
	public String getProperty_id() {
		return property_id;
	}
	public void setProperty_id(String property_id) {
		this.property_id = property_id;
	}
	public String getProperty_split_id() {
		return property_split_id;
	}
	public void setProperty_split_id(String property_split_id) {
		this.property_split_id = property_split_id;
	}
    
}

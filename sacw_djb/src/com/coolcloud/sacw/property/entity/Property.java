package com.coolcloud.sacw.property.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.coolcloud.sacw.common.BaseEntity;
import com.coolcloud.sacw.photo.entity.Photo;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 财物信息实体类
 * @author 王腾飞
 *2018年12月28日上午11:01:56
 */
public class Property  extends BaseEntity{

    private static final long serialVersionUID = 1L;

    
    
    
    
    /**
     * `id` varchar(32) NOT NULL COMMENT '财物id',
  `case_id` varchar(32) DEFAULT NULL COMMENT '案件ID',
  `property_name` varchar(100) DEFAULT NULL COMMENT '财物名称',
  `qr_code` varchar(100) DEFAULT NULL COMMENT '二维码',
  `property_type_code` varchar(32) DEFAULT NULL COMMENT '财物类型编码',
  `property_type` varchar(100) DEFAULT NULL COMMENT '财物类型',
  `number` int(11) DEFAULT NULL COMMENT '数量',
  `register_date` date DEFAULT NULL COMMENT '登记日期',
  `property_status` varchar(100) DEFAULT NULL COMMENT '财物状态',
  `remake` varchar(1500) DEFAULT NULL COMMENT '备注',
  `kwbm` varchar(100) DEFAULT NULL COMMENT '储存位置编码（原位置，保管中心位置请用kwbm）',
  `kwmc` varchar(100) DEFAULT NULL COMMENT '储存位置名称（原位置，保管中心位置请用kwmc）',
  `deleted` int(1) DEFAULT NULL COMMENT '是否删除',
     */
   // private  String  id;
    private String caseId;// 案件ID
  
    private String propertyName; // 财务名称
    private String qrCode; // 二维码
    private String propertyTypeCode; // 财务类型编码
    private String propertyType; // 财务类型
    private Integer number; // 数量
    private Date register_date; // 登记日期
    private String propertyStatus; // 财物状态
    private String propertyStatusCode; // 财物状态编码
    private String remake; // 备注
    private String kwbm;   //储存位置编码
    private String kwmc;   //储存位置名称
    
    private String caseName;//案件名称
    
    private  String   organizerName;//财物权限单位
    
    private String  organizerCode;//财物所属案件的单位编码
    
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getQrCode() {
		return qrCode;
	}
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	public String getPropertyTypeCode() {
		return propertyTypeCode;
	}
	public void setPropertyTypeCode(String propertyTypeCode) {
		this.propertyTypeCode = propertyTypeCode;
	}
	public String getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Date getRegister_date() {
		return register_date;
	}
	public void setRegister_date(Date register_date) {
		this.register_date = register_date;
	}
	public String getPropertyStatus() {
		return propertyStatus;
	}
	public void setPropertyStatus(String propertyStatus) {
		this.propertyStatus = propertyStatus;
	}
	public String getPropertyStatusCode() {
		return propertyStatusCode;
	}
	public void setPropertyStatusCode(String propertyStatusCode) {
		this.propertyStatusCode = propertyStatusCode;
	}
	public String getRemake() {
		return remake;
	}
	public void setRemake(String remake) {
		this.remake = remake;
	}
	public String getKwbm() {
		return kwbm;
	}
	public void setKwbm(String kwbm) {
		this.kwbm = kwbm;
	}
	public String getKwmc() {
		return kwmc;
	}
	public void setKwmc(String kwmc) {
		this.kwmc = kwmc;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getOrganizerName() {
		return organizerName;
	}
	public void setOrganizerName(String organizerName) {
		this.organizerName = organizerName;
	}
	
	
	public String getOrganizerCode() {
		return organizerCode;
	}
	public void setOrganizerCode(String organizerCode) {
		this.organizerCode = organizerCode;
	}
	public void setPhotos(List<Photo> photos) {
		// TODO Auto-generated method stub
		
	}
    
  
   
   
   

	/*@JsonIgnore
    private String splitId;*/

   

	
    
    
    
    
}

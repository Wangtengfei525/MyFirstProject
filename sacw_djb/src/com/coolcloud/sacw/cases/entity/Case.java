package com.coolcloud.sacw.cases.entity;

import java.sql.Timestamp;

import com.coolcloud.sacw.common.BaseEntity;

/**
 * 案件基本信息实体类
 * @author 王腾飞
 *2018年12月28日上午10:48:30
 */
public class Case extends BaseEntity {
	private static final long serialVersionUID = 2552527551938382895L;

	private String caseName; // 案件名称
	private String caseTypeName; // 案件类型名称
	private String caseTypeCode; // 案件类型编码
	private String organizerType; // 承办单位类型
	private String organizerCode; // 承办单位编码
	private String organizerName; // 承办单位名称
	private String undertakerName; // 承办人姓名
	private Timestamp createTime; // 创建时间
	private Timestamp updateTime; // 更新时间
    private String remark;//备注
    private  String   organizerTypeCode;   //组织类型编码
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	public String getCaseTypeName() {
		return caseTypeName;
	}
	public void setCaseTypeName(String caseTypeName) {
		this.caseTypeName = caseTypeName;
	}
	public String getCaseTypeCode() {
		return caseTypeCode;
	}
	public void setCaseTypeCode(String caseTypeCode) {
		this.caseTypeCode = caseTypeCode;
	}
	public String getOrganizerType() {
		return organizerType;
	}
	public void setOrganizerType(String organizerType) {
		this.organizerType = organizerType;
	}
	public String getOrganizerCode() {
		return organizerCode;
	}
	public void setOrganizerCode(String organizerCode) {
		this.organizerCode = organizerCode;
	}
	public String getOrganizerName() {
		return organizerName;
	}
	public void setOrganizerName(String organizerName) {
		this.organizerName = organizerName;
	}
	public String getUndertakerName() {
		return undertakerName;
	}
	public void setUndertakerName(String undertakerName) {
		this.undertakerName = undertakerName;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOrganizerTypeCode() {
		return organizerTypeCode;
	}
	public void setOrganizerTypeCode(String organizerTypeCode) {
		this.organizerTypeCode = organizerTypeCode;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Case() {
		super();
		// TODO Auto-generated constructor stub
	}
	
    
    
    
   
}

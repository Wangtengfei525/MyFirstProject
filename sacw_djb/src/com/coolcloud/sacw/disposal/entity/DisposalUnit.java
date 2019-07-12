package com.coolcloud.sacw.disposal.entity;


import com.coolcloud.sacw.common.BaseEntity;


public class DisposalUnit extends  BaseEntity{
	/**
	 *财物id
	 */
    private String propertyId;
    /**
     * 财物名称
     */
    private String propertyName;
    /**
     * 案件id
     */
    private String caseId;
    /**
     * 案件名称
     */
    private String caseName;
     /**
      * 发送单位编码
      */
    private String sendUnitCode;
    /**
     * 发送单位名称
     */
    private String sendUnitName;
    /**
     * 备注
     */
    private String back;
    /**
     * 接收单位编码
     */
    private String receiveUnitCode;
    /**
     * 接收单位名称
     */
    private String receiveUnitName;
    /**
     * 处置权单位
     */
    private String disposalUnitCode;
    /**
     * 处置权单位名称
     */
    private String disposalUnitName;
    /**
     * 处置权单位类型
     */
    private String disposalType;
	public String getBack() {
		return back;
	}
	public void setBack(String back) {
		this.back = back;
	}
	public String getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
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
	public String getSendUnitCode() {
		return sendUnitCode;
	}
	public void setSendUnitCode(String sendUnitCode) {
		this.sendUnitCode = sendUnitCode;
	}
	public String getSendUnitName() {
		return sendUnitName;
	}
	public void setSendUnitName(String sendUnitName) {
		this.sendUnitName = sendUnitName;
	}
	public String getReceiveUnitCode() {
		return receiveUnitCode;
	}
	public void setReceiveUnitCode(String receiveUnitCode) {
		this.receiveUnitCode = receiveUnitCode;
	}
	public String getReceiveUnitName() {
		return receiveUnitName;
	}
	public void setReceiveUnitName(String receiveUnitName) {
		this.receiveUnitName = receiveUnitName;
	}
	public String getDisposalUnitCode() {
		return disposalUnitCode;
	}
	public void setDisposalUnitCode(String disposalUnitCode) {
		this.disposalUnitCode = disposalUnitCode;
	}
	public String getDisposalUnitName() {
		return disposalUnitName;
	}
	public void setDisposalUnitName(String disposalUnitName) {
		this.disposalUnitName = disposalUnitName;
	}
	public String getDisposalType() {
		return disposalType;
	}
	public void setDisposalType(String disposalType) {
		this.disposalType = disposalType;
	}
    
}

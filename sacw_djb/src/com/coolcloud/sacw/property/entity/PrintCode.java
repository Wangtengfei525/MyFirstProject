package com.coolcloud.sacw.property.entity;
/**
 * 条形码
 * @author 王腾飞
 *2018年12月28日上午10:59:47
 */
public class PrintCode {
    private String caseName;  //案件名称
    private String propertyName;  //财物名称
    private String qrCode;   //二维码
    private Integer number;  //财物数量
    private String saveLocationMc;  //存储位置
    
    public PrintCode(String caseName,String propertyName,String qrCode,Integer number,String saveLocationMc){
    	this.caseName = caseName;
    	this.propertyName = propertyName;
    	this.qrCode = qrCode;
    	this.number = number;
    	this.saveLocationMc = saveLocationMc; 
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
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getSaveLocationMc() {
		return saveLocationMc;
	}
	public void setSaveLocationMc(String saveLocationMc) {
		this.saveLocationMc = saveLocationMc;
	}
    
}

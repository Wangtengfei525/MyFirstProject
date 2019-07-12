package com.coolcloud.sacw.property.entity;

import java.sql.Date;

import com.coolcloud.sacw.common.BaseExample;

/**
 * 财务管理条件查询实体类
 * 
 * @author 王孝康
 *
 */

public class PropertyExample extends BaseExample {

    private String caseId;

    private String qrCode;

    private String caseName;

    private String propertyName;

    private String propertyTypeCode;

    private String goodsSpecial;

    private String platCaseCode;

    private String saveLocationCode;

    private String property_number;

    private String propertyStatus;

    private String propertyStatusCode;

    private String ntag;

    private Date registerDate; // 登记日期

    private Date updateTime;

    private String sendUnitId;

    private String splited;

    /**
     * 为true时不查询登记状态财物
     */
    private Boolean notRegister;

    /**
     * 综合条件（案件名称、财物名称、二维码）
     */
    private String composite;

    /**
     * 库位编码，用户查询柜子及其下柜子中的物品
     */
    private String kwbm;

    public PropertyExample() {

    }

    public PropertyExample(String platCaseCode, Integer deleted) {
        this.platCaseCode = platCaseCode;
        super.setDeleted(deleted);
    }

    public String getCaseId() {
        return caseId;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
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

    public String getProperty_number() {
        return property_number;
    }

    public void setProperty_number(String property_number) {
        this.property_number = property_number;
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

    public String getNtag() {
        return ntag;
    }

    public void setNtag(String ntag) {
        this.ntag = ntag;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getKwbm() {
        return kwbm;
    }

    public void setKwbm(String kwbm) {
        this.kwbm = kwbm;
    }

    public String getSendUnitId() {
        return sendUnitId;
    }

    public void setSendUnitId(String sendUnitId) {
        this.sendUnitId = sendUnitId;
    }

    public String getSplited() {
        return splited;
    }

    public void setSplited(String splited) {
        this.splited = splited;
    }

    public String getComposite() {
        return composite;
    }
     
    public void setComposite(String composite) {
        this.composite = composite;
    }

    public Boolean getNotRegister() {
        return notRegister;
    }

    public void setNotRegister(Boolean notRegister) {
        this.notRegister = notRegister;
    }

}

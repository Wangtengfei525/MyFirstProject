package com.coolcloud.sacw.car.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.coolcloud.sacw.common.BaseEntity;

/**
 * 车辆养护实体类
 * 
 * @author
 *
 */
public class CarConserve extends BaseEntity {

    private static final long serialVersionUID = 2988705031919759599L;

    /**
     * 财物id
     */
    private String propertyId;

    /**
     * 二维码
     */
    @NotBlank(message = "二维码不能为空")
    private String qrCode;

    /**
     * 案件名称
     */
    private String caseName;

    /**
     * 平台案件编号
     */
    private String platCaseCode;

    /**
     * 养护时间
     */
    @NotNull(message = "养护日期不可为空")
    private Date conserveTime;
    /**
     * 养护人
     */
    @NotBlank(message = "养护人不可为空")
    private String conserveMan;

    /**
     * 养护备注
     */
    private String conserveRemark;

    /**
     * 养护内容
     */
    @NotBlank(message = "养护内容不能为空")
    private String conserveText;
    /**
     * 审核人
     */
    private String auditMan;
    /**
     * 审核备注
     */
    private String auditRemark;
    /**
     * 状态
     */
    private String status;
    /**
     * 交换钥匙
     */
    private String exchangeKey;
    /**
     * 养护批号
     */
    private String conserveNumber;
    /**
     * 车辆信息
     */
    private String carMessage;
    /**
     * 养护内容名称
     */
    @NotBlank
    private String conserveTextName;
    /**
     * 财物名称
     */
    private String propertyName;
    /**
     * 预计时间
     */
    private Double predictTime;
    /**
     * 财物保存位置
     */
    private String propertyLocation;

    /**
     * 权限单位
     */
    private String permitUnitName;

    /**
     * 分组id
     */
    private String groupId;

    /**
     * 财物拆分id
     */
    private String splitId;

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getPlatCaseCode() {
        return platCaseCode;
    }

    public void setPlatCaseCode(String platCaseCode) {
        this.platCaseCode = platCaseCode;
    }

    public Date getConserveTime() {
        return conserveTime;
    }

    public void setConserveTime(Date conserveTime) {
        this.conserveTime = conserveTime;
    }

    public String getConserveMan() {
        return conserveMan;
    }

    public void setConserveMan(String conserveMan) {
        this.conserveMan = conserveMan;
    }

    public String getConserveRemark() {
        return conserveRemark;
    }

    public void setConserveRemark(String conserveRemark) {
        this.conserveRemark = conserveRemark;
    }

    public String getConserveText() {
        return conserveText;
    }

    public void setConserveText(String conserveText) {
        this.conserveText = conserveText;
    }

    public String getAuditMan() {
        return auditMan;
    }

    public void setAuditMan(String auditMan) {
        this.auditMan = auditMan;
    }

    public String getAuditRemark() {
        return auditRemark;
    }

    public void setAuditRemark(String auditRemark) {
        this.auditRemark = auditRemark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExchangeKey() {
        return exchangeKey;
    }

    public void setExchangeKey(String exchangeKey) {
        this.exchangeKey = exchangeKey;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getCarMessage() {
        return carMessage;
    }

    public void setCarMessage(String carMessage) {
        this.carMessage = carMessage;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Double getPredictTime() {
        return predictTime;
    }

    public void setPredictTime(Double predictTime) {
        this.predictTime = predictTime;
    }

    public String getPropertyLocation() {
        return propertyLocation;
    }

    public void setPropertyLocation(String propertyLocation) {
        this.propertyLocation = propertyLocation;
    }

    public String getConserveTextName() {
        return conserveTextName;
    }

    public void setConserveTextName(String conserveTextName) {
        this.conserveTextName = conserveTextName;
    }

    public String getConserveNumber() {
        return conserveNumber;
    }

    public void setConserveNumber(String conserveNumber) {
        this.conserveNumber = conserveNumber;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getPermitUnitName() {
        return permitUnitName;
    }

    public void setPermitUnitName(String permitUnitName) {
        this.permitUnitName = permitUnitName;
    }

    public String getSplitId() {
        return splitId;
    }

    public void setSplitId(String splitId) {
        this.splitId = splitId;
    }
}

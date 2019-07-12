package com.coolcloud.sacw.operation.entity;

import java.util.Date;

import com.coolcloud.sacw.common.BaseEntity;

public class Secondment extends BaseEntity {

    private static final long serialVersionUID = -3360871841464408487L;

    private String exchangeId;

    private String propertyId;

    private String propertyName;

    private String caseId;

    private String caseName;

    private Date secondmentTime;

    private Date expectedReturnTime;

    private Date returnTime;

    private String secondmentUnitId;

    private String secondmentUnitName;

    private Integer returned;

    private Integer overdueDays;

    public String getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(String exchangeId) {
        this.exchangeId = exchangeId;
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

    public Date getSecondmentTime() {
        return secondmentTime;
    }

    public void setSecondmentTime(Date secondmentTime) {
        this.secondmentTime = secondmentTime;
    }

    public Date getExpectedReturnTime() {
        return expectedReturnTime;
    }

    public void setExpectedReturnTime(Date expectedReturnTime) {
        this.expectedReturnTime = expectedReturnTime;
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    public String getSecondmentUnitId() {
        return secondmentUnitId;
    }

    public void setSecondmentUnitId(String secondmentUnitId) {
        this.secondmentUnitId = secondmentUnitId;
    }

    public String getSecondmentUnitName() {
        return secondmentUnitName;
    }

    public void setSecondmentUnitName(String secondmentUnitName) {
        this.secondmentUnitName = secondmentUnitName;
    }

    public Integer getReturned() {
        return returned;
    }

    public void setReturned(Integer returned) {
        this.returned = returned;
    }

    public Integer getOverdueDays() {
        return overdueDays;
    }

    public void setOverdueDays(Integer overdueDays) {
        this.overdueDays = overdueDays;
    }

}

package com.coolcloud.sacw.car.entity;

import java.util.Date;

import com.coolcloud.sacw.common.BaseExample;

public class CarConserveExample extends BaseExample {

    private String caseName;

    private String caseNameLike;

    private Date conserveTime;

    private String qrCode;

    private Date startTime;

    private Date endTime;

    private String groupId;

    public String getCaseNameLike() {
        return caseNameLike;
    }

    public void setCaseNameLike(String caseNameLike) {
        this.caseNameLike = caseNameLike;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public Date getConserveTime() {
        return conserveTime;
    }

    public void setConserveTime(Date conserveTime) {
        this.conserveTime = conserveTime;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    
    
}

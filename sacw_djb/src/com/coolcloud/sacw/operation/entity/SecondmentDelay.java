package com.coolcloud.sacw.operation.entity;

import java.util.Date;

import com.coolcloud.sacw.common.BaseEntity;

/**
 */
public class SecondmentDelay extends BaseEntity {

    private static final long serialVersionUID = -5169529799178215582L;

    /**
     * 借调记录id
     */
    private String secondmentId;

    /**
     * 延期时间
     *
     */
    private Date delayTime;

    /**
     * 原预计归还时间
     */
    private Date expectedReturnTime;

    /**
     * 延期后归还时间
     *
     */
    private Date delayedReturnTime;

    /**
     * 延期备注
     *
     */
    private String remark;

    public String getSecondmentId() {
        return secondmentId;
    }

    public void setSecondmentId(String secondmentId) {
        this.secondmentId = secondmentId;
    }

    public Date getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(Date delayTime) {
        this.delayTime = delayTime;
    }

    public Date getExpectedReturnTime() {
        return expectedReturnTime;
    }

    public void setExpectedReturnTime(Date expectedReturnTime) {
        this.expectedReturnTime = expectedReturnTime;
    }

    public Date getDelayedReturnTime() {
        return delayedReturnTime;
    }

    public void setDelayedReturnTime(Date delayedReturnTime) {
        this.delayedReturnTime = delayedReturnTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
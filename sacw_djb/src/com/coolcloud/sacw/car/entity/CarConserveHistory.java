package com.coolcloud.sacw.car.entity;

import java.util.Date;

import com.coolcloud.sacw.common.BaseEntity;

/**
 * 养护历史记录
 * 
 * @author xyz
 *
 * @date 2018年4月12日 下午3:59:32
 */
public class CarConserveHistory extends BaseEntity {

    private static final long serialVersionUID = 3392097705210981350L;

    /**
     * 财物id
     */
    private String propertyId;

    /**
     * 财物拆分id
     */
    private String splitId;

    /**
     * 养护id
     *
     */
    private String conserveId;

    /**
     * 养护时间
     */
    private Date conserveTime;

    /**
     * 养护内容代码
     *
     */
    private String conserveContentCode;

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getConserveId() {
        return conserveId;
    }

    public void setConserveId(String conserveId) {
        this.conserveId = conserveId;
    }

    public String getSplitId() {
        return splitId;
    }

    public void setSplitId(String splitId) {
        this.splitId = splitId;
    }

    public Date getConserveTime() {
        return conserveTime;
    }

    public void setConserveTime(Date conserveTime) {
        this.conserveTime = conserveTime;
    }

    public String getConserveContentCode() {
        return conserveContentCode;
    }

    public void setConserveContentCode(String conserveContentCode) {
        this.conserveContentCode = conserveContentCode;
    }

}
package com.coolcloud.sacw.property.entity;

import java.util.Date;

import com.coolcloud.sacw.common.BaseEntity;

/**
 * 财物操作日志
 */
public class PropertyLog extends BaseEntity {

    private static final long serialVersionUID = -4417251895400634890L;

    /**
     * 财物id
     *
     */
    private String propertyId;

    /**
     * 操作时间
     */
    private Date operationTime;

    /**
     * 操作人
     *
     */
    private String operationUser;

    /**
     * 操作内容
     */
    private String operationContent;

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public String getOperationUser() {
        return operationUser;
    }

    public void setOperationUser(String operationUser) {
        this.operationUser = operationUser;
    }

    public String getOperationContent() {
        return operationContent;
    }

    public void setOperationContent(String operationContent) {
        this.operationContent = operationContent;
    }

}
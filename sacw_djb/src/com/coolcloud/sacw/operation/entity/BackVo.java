package com.coolcloud.sacw.operation.entity;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

public class BackVo {

    @NotEmpty(message = "财物id不能为空")
    private String id;

    
    @NotEmpty(message = "临时附件id不能为空")
    private String tempId;

    private Date operationTime;

    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTempId() {
        return tempId;
    }

    public void setTempId(String tempId) {
        this.tempId = tempId;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

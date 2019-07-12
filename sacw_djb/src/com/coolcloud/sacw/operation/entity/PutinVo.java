package com.coolcloud.sacw.operation.entity;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

public class PutinVo {

    @NotEmpty(message = "交换记录id不能为空")
    private String id;

    @NotEmpty(message = "临时附件id不能为空")
    private String tempId;

    private String operationTime;

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


    public String getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(String operationTime) {
		this.operationTime = operationTime;
	}

	public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}

package com.coolcloud.sacw.operation.entity;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

public class SecondmentVo {

    @NotEmpty(message = "交换记录id不能为空")
    private String id;

    @NotEmpty(message = "临时附件id不能为空")
    private String tempId;

    private Date operationTime;

    private Date expectedReturnTime;

    private String remark;
    
    private  String caseName;

    
    
    
    
    
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

    public Date getExpectedReturnTime() {
        return expectedReturnTime;
    }

    public void setExpectedReturnTime(Date expectedReturnTime) {
        this.expectedReturnTime = expectedReturnTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

    
}

package com.coolcloud.sacw.operation.entity;

import java.io.Serializable;
import java.util.Date;

public class SearchVo   implements  Serializable {

    private Integer page;

    private Integer rows;

    /**
     * 综合查询条件：案件名称、案件编号
     */
    private String composite;

    private Integer status;

    private String sendUnitId;

    private String receiveUnitId;

    private String nodeCode;

    private String receiveState;

    private Date startTime;

    private Date endTime;
    
    private   String   remark;
    
    private  String propertyStatusCode;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getComposite() {
        return composite;
    }

    public void setComposite(String composite) {
        this.composite = composite;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSendUnitId() {
        return sendUnitId;
    }

    public void setSendUnitId(String sendUnitId) {
        this.sendUnitId = sendUnitId;
    }

    public String getReceiveUnitId() {
        return receiveUnitId;
    }

    public void setReceiveUnitId(String receiveUnitId) {
        this.receiveUnitId = receiveUnitId;
    }

    public String getNodeCode() {
        return nodeCode;
    }

    public void setNodeCode(String nodeCode) {
        this.nodeCode = nodeCode;
    }

    public String getReceiveState() {
        return receiveState;
    }

    public void setReceiveState(String receiveState) {
        this.receiveState = receiveState;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPropertyStatusCode() {
		return propertyStatusCode;
	}

	public void setPropertyStatusCode(String propertyStatusCode) {
		this.propertyStatusCode = propertyStatusCode;
	}

    
}

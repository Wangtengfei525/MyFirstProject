package com.coolcloud.sacw.property.entity;

import java.util.Date;

public class PropertyVo {
	private Integer page;

    private Integer rows;

    /**
     * 综合查询条件：案件名称、案件编号
     */
    private String composite;
    private  Integer  status;
    

    private String  propertyStatusCode;   //财物状态编码
    
   /* private Date startTime;           //查询条件的时候的开始时间

    private Date endTime; */
    
    private  String   startTime;
    private String endTime;
    
    
    
    
    
    //查询条件开始的时候的结束条件
    private    String  remake;
    
    private  String sendUnitId;//单位编码
    
    
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

	public String getPropertyStatusCode() {
		return propertyStatusCode;
	}

	public void setPropertyStatusCode(String propertyStatusCode) {
		this.propertyStatusCode = propertyStatusCode;
	}
/*
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
*/
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemake() {
		return remake;
	}

	public void setRemake(String remake) {
		this.remake = remake;
	}

	public String getSendUnitId() {
		return sendUnitId;
	}

	public void setSendUnitId(String sendUnitId) {
		this.sendUnitId = sendUnitId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
    
	    
	   
	
}

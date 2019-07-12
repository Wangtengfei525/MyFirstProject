package com.coolcloud.sacw.operation.entity;

import java.util.Date;

import com.mysql.fabric.xmlrpc.base.Data;

public class NewSearchVo {
	public String composite;
	public String status;
	public String sendUnitId;
	public Date startTime;
	public Date endTime;
	public Integer page;
	public Integer rows;
	
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSendUnitId() {
		return sendUnitId;
	}
	public void setSendUnitId(String sendUnitId) {
		this.sendUnitId = sendUnitId;
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
	
	
}

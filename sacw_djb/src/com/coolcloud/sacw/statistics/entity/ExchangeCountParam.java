package com.coolcloud.sacw.statistics.entity;

import java.util.Date;

public class ExchangeCountParam {

    private String nodeCode;
    private Integer containChildren;
    private String organizerCode;
    private  Date   startTime;
    private  Date  endTime; 
    private  String   propertyStatusCode;
    
    
    
	public String getNodeCode() {
		return nodeCode;
	}
	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}
	public Integer getContainChildren() {
		return containChildren;
	}
	public void setContainChildren(Integer containChildren) {
		this.containChildren = containChildren;
	}
	public String getOrganizerCode() {
		return organizerCode;
	}
	public void setOrganizerCode(String organizerCode) {
		this.organizerCode = organizerCode;
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
	public String getPropertyStatusCode() {
		return propertyStatusCode;
	}
	public void setPropertyStatusCode(String propertyStatusCode) {
		this.propertyStatusCode = propertyStatusCode;
	}
    

   
   

   
}

package com.coolcloud.sacw.system.entity;

import java.util.Date;

import com.coolcloud.sacw.common.BaseExample;

public class LogExample extends BaseExample {

	private Integer type;

	private String content;

	private String contentLike;

	private Date logTime;

	private Date logTimeGreaterThan;

	private Date logTimeLessThan;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentLike() {
		return contentLike;
	}

	public void setContentLike(String contentLike) {
		this.contentLike = contentLike;
	}

	public Date getLogTime() {
		return logTime;
	}

	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}

	public Date getLogTimeGreaterThan() {
		return logTimeGreaterThan;
	}

	public void setLogTimeGreaterThan(Date logTimeGreaterThan) {
		this.logTimeGreaterThan = logTimeGreaterThan;
	}

	public Date getLogTimeLessThan() {
		return logTimeLessThan;
	}

	public void setLogTimeLessThan(Date logTimeLessThan) {
		this.logTimeLessThan = logTimeLessThan;
	}

}
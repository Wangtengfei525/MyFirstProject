package com.coolcloud.sacw.system.entity;

import java.util.Date;

import com.coolcloud.sacw.common.BaseEntity;

/**
 * 系统日志
 * @author xyz
 *
 */
public class Log extends BaseEntity {

	private static final long serialVersionUID = 3462128152682280354L;

	private Integer type;

	private String content;

	private Date logTime;

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

	public Date getLogTime() {
		return logTime;
	}

	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}


}
package com.coolcloud.sacw.annex.entity;

import java.sql.Timestamp;

import com.coolcloud.sacw.common.BaseExample;

public class File1Example extends BaseExample{
	
	private String caseId;
	private String file_class;
	private Timestamp crateTimeStart;
	private Timestamp crateTimeEnd;
	private String exchangeId;
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getFile_class() {
		return file_class;
	}
	public void setFile_class(String file_class) {
		this.file_class = file_class;
	}
	public Timestamp getCrateTimeStart() {
		return crateTimeStart;
	}
	public void setCrateTimeStart(Timestamp crateTimeStart) {
		this.crateTimeStart = crateTimeStart;
	}
	public Timestamp getCrateTimeEnd() {
		return crateTimeEnd;
	}
	public void setCrateTimeEnd(Timestamp crateTimeEnd) {
		this.crateTimeEnd = crateTimeEnd;
	}
	public String getExchangeId() {
		return exchangeId;
	}
	public void setExchangeId(String exchangeId) {
		this.exchangeId = exchangeId;
	}
	@Override
	public String toString() {
		return "File1Example [caseId=" + caseId + ", file_class=" + file_class + ", crateTimeStart=" + crateTimeStart
				+ ", crateTimeEnd=" + crateTimeEnd + ", exchangeId=" + exchangeId + "]";
	}
	public File1Example() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	
	
}

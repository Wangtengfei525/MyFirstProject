package com.coolcloud.sacw.cases.entity;

public class NewCase {
	public String caseName;
	public String organizerName;
	public String undertakerName;
	public String bz;
	public String create_time;
	public String case_name;
	public String organizer_name;
	
	
	public String getCase_name() {
		return case_name;
	}
	public void setCase_name(String case_name) {
		this.case_name = case_name;
	}
	public String getOrganizer_name() {
		return organizer_name;
	}
	public void setOrganizer_name(String organizer_name) {
		this.organizer_name = organizer_name;
	}
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	public String getOrganizerName() {
		return organizerName;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public void setOrganizerName(String organizerName) {
		this.organizerName = organizerName;
	}
	public String getUndertakerName() {
		return undertakerName;
	}
	public void setUndertakerName(String undertakerName) {
		this.undertakerName = undertakerName;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
}

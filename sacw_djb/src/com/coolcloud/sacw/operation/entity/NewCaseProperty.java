package com.coolcloud.sacw.operation.entity;

import com.coolcloud.sacw.common.BaseEntity;

public class NewCaseProperty extends BaseEntity {
	
	private static final long serialVersionUID = 4122480072081484181L;
	
	public String id;
	public String case_name;
	public String create_time;
	public String remark;
	public String organizer_name;
	
	public NewCaseProperty(String id,String case_name,String create_time,String remark,String organizer_name) {
		super();
		this.id=id;
		this.case_name=case_name;
		this.create_time=create_time;
		this.remark=remark;
		this.organizer_name=organizer_name;
	}
	public NewCaseProperty() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCase_name() {
		return case_name;
	}
	public void setCase_name(String case_name) {
		this.case_name = case_name;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOrganizer_name() {
		return organizer_name;
	}
	public void setOrganizer_name(String organizer_name) {
		this.organizer_name = organizer_name;
	}
	
	
}

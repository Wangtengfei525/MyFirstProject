package com.coolcloud.sacw.person.entity;

import com.coolcloud.sacw.common.BaseExample;

public class PersonExample extends BaseExample {

	private String caseId;

	private String personName;

	private String platCaseCode;

	private String platPersonCode;

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getPlatCaseCode() {
		return platCaseCode;
	}

	public void setPlatCaseCode(String platCaseCode) {
		this.platCaseCode = platCaseCode;
	}

	public String getPlatPersonCode() {
		return platPersonCode;
	}

	public void setPlatPersonCode(String platPersonCode) {
		this.platPersonCode = platPersonCode;
	}

}

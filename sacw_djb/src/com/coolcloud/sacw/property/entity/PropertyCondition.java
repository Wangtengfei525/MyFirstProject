package com.coolcloud.sacw.property.entity;

public class PropertyCondition {
	
	
	private String caseId;
	
	 /**
     * 综合条件（案件名称、财物名称、二维码）
     */
    private String composite;
    
    private String caseName;
    private String propertyName;
    private String qrCode;

   
   
    
    
    
    
    private String propertyStatusCode;

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getComposite() {
		return composite;
	}

	public void setComposite(String composite) {
		this.composite = composite;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyStatusCode() {
		return propertyStatusCode;
	}

	public void setPropertyStatusCode(String propertyStatusCode) {
		this.propertyStatusCode = propertyStatusCode;
	}

	public PropertyCondition(String caseId, String composite, String qrCode, String caseName, String propertyName,
			String propertyStatusCode) {
		super();
		this.caseId = caseId;
		this.composite = composite;
		this.qrCode = qrCode;
		this.caseName = caseName;
		this.propertyName = propertyName;
		this.propertyStatusCode = propertyStatusCode;
	}

	
	
	
}

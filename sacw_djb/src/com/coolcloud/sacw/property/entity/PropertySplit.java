package com.coolcloud.sacw.property.entity;

import com.coolcloud.sacw.common.BaseEntity;

/**
 * 财物拆分
 * 
 * @author 袁永祥
 *
 * @date 2017年12月13日 下午6:45:48
 */
public class PropertySplit extends BaseEntity {

    private static final long serialVersionUID = -5991326806053632240L;

    /**
     * 原财物名称
     */
    private String propertyName;

    /**
     * 原财物数量
     */
    private Integer propertyAmount;

    /**
     * 对应案件id
     */
    private String caseId;

    /**
     * 对应案件id
     */
    private String caseName;

    /**
     * 对应财物id
     */
    private String propertyId;

    /**
     * 拆分后名称
     */
    private String splitName;

    /**
     * 拆分后类型
     */
    private String splitTypeName;

    /**
     * 拆分后类型代码
     */
    private String splitTypeCode;

    /**
     * 拆分后数量
     */
    private Integer splitAmount;

    /**
     * 拆分后体积
     */
    private Double splitVolume;

    /**
     * 拆分备注
     */
    private String splitRemark;
    /**
     * 拆分后储物柜名称
     */
    private String splitSaveLocationName;
    
   
    public String kwmc;
    public String id;
    public String property_name;
    public String case_name;
   
    public String getKwmc() {
		return kwmc;
	}

	public void setKwmc(String kwmc) {
		this.kwmc = kwmc;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProperty_name() {
		return property_name;
	}

	public void setProperty_name(String property_name) {
		this.property_name = property_name;
	}

	public String getCase_name() {
		return case_name;
	}

	public void setCase_name(String case_name) {
		this.case_name = case_name;
	}

	/**
     * 拆分后储物柜代码
     */
    private String splitSaveLocationCode;

    public String getSplitSaveLocationName() {
		return splitSaveLocationName;
	}

	public void setSplitSaveLocationName(String splitSaveLocationName) {
		this.splitSaveLocationName = splitSaveLocationName;
	}

	public String getSplitSaveLocationCode() {
		return splitSaveLocationCode;
	}

	public void setSplitSaveLocationCode(String splitSaveLocationCode) {
		this.splitSaveLocationCode = splitSaveLocationCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Integer getPropertyAmount() {
        return propertyAmount;
    }

    public void setPropertyAmount(Integer propertyAmount) {
        this.propertyAmount = propertyAmount;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getSplitName() {
        return splitName;
    }

    public void setSplitName(String splitName) {
        this.splitName = splitName;
    }

    public String getSplitTypeName() {
        return splitTypeName;
    }

    public void setSplitTypeName(String splitTypeName) {
        this.splitTypeName = splitTypeName;
    }

    public String getSplitTypeCode() {
        return splitTypeCode;
    }

    public void setSplitTypeCode(String splitTypeCode) {
        this.splitTypeCode = splitTypeCode;
    }

    public Integer getSplitAmount() {
        return splitAmount;
    }

    public void setSplitAmount(Integer splitAmount) {
        this.splitAmount = splitAmount;
    }

    public Double getSplitVolume() {
        return splitVolume;
    }

    public void setSplitVolume(Double splitVolume) {
        this.splitVolume = splitVolume;
    }

    public String getSplitRemark() {
        return splitRemark;
    }

    public void setSplitRemark(String splitRemark) {
        this.splitRemark = splitRemark;
    }

}

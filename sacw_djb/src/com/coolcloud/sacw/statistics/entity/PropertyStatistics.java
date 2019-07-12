package com.coolcloud.sacw.statistics.entity;

/**
 * 财物统计
 * 
 * @author xyz
 *
 * @date 2018年4月14日 下午5:29:59
 */
public class PropertyStatistics {

    /**
     * 单位
     */
    private String unitName;
   

    /**
     * 入库物品数
     */
    private Integer propertyIn;
    
    
    /**
     * 出库财物数
     */
    private Integer propertyOut;

    
    /**
     * 借调物品数
     */
    private Integer propertySecondment;

    
    /**
     * 归还物品数
     */
    private Integer propertyBack;

    
    /**
     * 总案件数
     */
    private Integer caseAll;

    /**
     * 总物品数
     */
    private Integer propertyAll;
    
 

    /**
     * 总入库物品数
     */
    private Integer propertyAllIn;

    
    /**
     * 总出库物品数
     */
    private Integer propertyAllOut;


	public String getUnitName() {
		return unitName;
	}


	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}


	public Integer getPropertyIn() {
		return propertyIn;
	}


	public void setPropertyIn(Integer propertyIn) {
		this.propertyIn = propertyIn;
	}


	public Integer getPropertyOut() {
		return propertyOut;
	}


	public void setPropertyOut(Integer propertyOut) {
		this.propertyOut = propertyOut;
	}


	public Integer getPropertySecondment() {
		return propertySecondment;
	}


	public void setPropertySecondment(Integer propertySecondment) {
		this.propertySecondment = propertySecondment;
	}


	public Integer getPropertyBack() {
		return propertyBack;
	}


	public void setPropertyBack(Integer propertyBack) {
		this.propertyBack = propertyBack;
	}


	public Integer getCaseAll() {
		return caseAll;
	}


	public void setCaseAll(Integer caseAll) {
		this.caseAll = caseAll;
	}


	public Integer getPropertyAll() {
		return propertyAll;
	}


	public void setPropertyAll(Integer propertyAll) {
		this.propertyAll = propertyAll;
	}


	public Integer getPropertyAllIn() {
		return propertyAllIn;
	}


	public void setPropertyAllIn(Integer propertyAllIn) {
		this.propertyAllIn = propertyAllIn;
	}


	public Integer getPropertyAllOut() {
		return propertyAllOut;
	}


	public void setPropertyAllOut(Integer propertyAllOut) {
		this.propertyAllOut = propertyAllOut;
	}
    
    
    
    
    
    
    
    
    

    
}

package com.coolcloud.sacw.property.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

/**
 * 添加拆分财物表单
 * 
 * @author xyz
 *
 */
public class PropertySplitAddForm {

    /**
     * 财物id
     */
    @NotBlank(message = "财物id不能为空")
    private String propertyId;

    /**
     * 拆分后名称
     */
    @NotBlank(message = "新名称不能为空")
    @Length(min = 1, max = 100, message = "新名称长度应在{min}至{max}之间")
    private String splitName;

    /**
     * 拆分后类型代码
     */
    @NotBlank(message = "新类型代码不能为空")
    @Length(min = 1, max = 50, message = "新名称长度应在{min}至{max}之间")
    private String splitTypeCode;

    /**
     * 拆分后数量
     */
    @NotNull(message = "拆分后数量不能为空")
    @Range(min = 1, max = Integer.MAX_VALUE, message = "拆分后数量应在{min}和{max}之间")
    private Integer splitAmount;

    /**
     * 拆分后体积
     */
    @Range(min = 0, max = Integer.MAX_VALUE, message = "拆分后体积应在{min}和{max}之间")
    private Double splitVolume;

    @Length(min = 0, max = 100, message = "拆分备注长度应在{min}至{max}之间")
    private String splitRemark;
    /**
     * 拆分后储物柜名称
     */
    @Length(min = 0, max = 100, message = "拆分后长度应在{min}至{max}之间")
    private String splitSaveLocationName;
    /**
     * 拆分后储物柜代码
     */
    @Length(min = 0, max = 100, message = "拆分后长度应在{min}至{max}之间")
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

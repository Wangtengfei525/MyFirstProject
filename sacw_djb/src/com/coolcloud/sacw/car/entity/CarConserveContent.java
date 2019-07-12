package com.coolcloud.sacw.car.entity;

import com.coolcloud.sacw.common.BaseEntity;

/**
 * 车辆养护内容
 * 
 * @author xyz
 *
 * @date 2018年4月11日 上午9:47:23
 */
public class CarConserveContent extends BaseEntity {

    private static final long serialVersionUID = 4409984347279455189L;

    /**
     * 财物拆分id
     */
    private String splitId;

    /**
     * 养护内容代码
     */
    private String conserveContentCode;

    /**
     * 养护内容名称
     */
    private String conserveContentName;

    /**
     * 是否启用
     */
    private Integer enabled;

    public String getSplitId() {
        return splitId;
    }

    public void setSplitId(String splitId) {
        this.splitId = splitId;
    }

    public String getConserveContentCode() {
        return conserveContentCode;
    }

    public void setConserveContentCode(String conserveContentCode) {
        this.conserveContentCode = conserveContentCode;
    }

    public String getConserveContentName() {
        return conserveContentName;
    }

    public void setConserveContentName(String conserveContentName) {
        this.conserveContentName = conserveContentName;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

}
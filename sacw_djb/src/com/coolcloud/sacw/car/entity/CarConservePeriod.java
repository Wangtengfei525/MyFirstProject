package com.coolcloud.sacw.car.entity;

import com.coolcloud.sacw.common.BaseEntity;

/**
 * 车辆养护周期
 * 
 * @author xyz
 *
 * @date 2018年4月10日 下午4:28:47
 */
public class CarConservePeriod extends BaseEntity {

    private static final long serialVersionUID = 6807934340306601288L;

    /**
     * 养护内容代码
     */
    private String conserveContentCode;

    /**
     * 养护内容
     */
    private String conserveContentName;

    /**
     * 养护周期（天）
     */
    private Integer conservePeriod;

    /**
     * 是否启用 1启用0禁用
     */
    private Integer enabled;

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

    public Integer getConservePeriod() {
        return conservePeriod;
    }

    public void setConservePeriod(Integer conservePeriod) {
        this.conservePeriod = conservePeriod;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

}
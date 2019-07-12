package com.coolcloud.sacw.statistics.entity;

import com.coolcloud.sacw.common.BaseEntity;

/**
 * 
 * @author xyz
 *
 * @date 2018年4月14日 下午5:54:12
 */
public class StatisticsUnit extends BaseEntity {

    private static final long serialVersionUID = -5571811886623466398L;

    /**
     * 单位id
     *
     */
    private String unitId;

    /**
     * 单位id
     *
     */
    private String unitName;

    /**
     * 统计类型
     */
    private String type;

    /**
     * 排序列
     *
     */
    private Integer sort;

    /**
     * 统计时是否要包含下级单位，0代表否       1代表是
     *
     */
    
    private Integer containsChildren;

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getContainsChildren() {
        return containsChildren;
    }

    public void setContainsChildren(Integer containsChildren) {
        this.containsChildren = containsChildren;
    }

}
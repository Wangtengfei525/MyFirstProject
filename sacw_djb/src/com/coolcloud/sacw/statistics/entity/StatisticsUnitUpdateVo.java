package com.coolcloud.sacw.statistics.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * @author xyz
 *
 * @date 2018年4月14日 下午5:54:12
 */
public class StatisticsUnitUpdateVo {

    /**
     * id
     *
     */
    @NotBlank(message = "统计单位id不能为空")
    private String id;

    /**
     * 单位id
     *
     */
    private String unitId;

    /**
     * 单位名称
     *
     */
    @Length(min = 1, max = 255, message = "单位名称长度应在{min}到{max}之间")
    private String unitName;

    /**
     * 统计类型
     */
    @NotEmpty(message = "类型不能为空")
    @Length(min = 1, max = 32, message = "类型长度应在{min}到{max}之间")
    private String type;

    /**
     * 排序列
     *
     */
    private Integer sort;

    /**
     * 统计时是否包含下级单位
     *
     */
    private Integer containsChildren;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
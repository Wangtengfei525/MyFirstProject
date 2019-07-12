package com.coolcloud.sacw.warning.entity;

import com.coolcloud.sacw.common.BaseEntity;

/**
 * 逾期设置实体类
 * 
 * @author
 *
 */
public class OverdueSet extends BaseEntity {

    private static final long serialVersionUID = -1361066870595080107L;

    /**
     * 状态编码
     */
    private String statusCode;

    /**
     * 逾期状态名称
     */
    private String statusName;// 登记、移送等....
    /**
     * 提前几天提醒
     */
    private Integer warningTime;
    /**
     * 到期天数
     */
    private Integer warningPeriod;

    private Integer page;

    private Integer rows;

    private String unitId;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Integer getWarningTime() {
        return warningTime;
    }

    public void setWarningTime(Integer warningTime) {
        this.warningTime = warningTime;
    }

    public Integer getWarningPeriod() {
        return warningPeriod;
    }

    public void setWarningPeriod(Integer warningPeriod) {
        this.warningPeriod = warningPeriod;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

}
